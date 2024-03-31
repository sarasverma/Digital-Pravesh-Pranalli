from fastapi import FastAPI, Body
import firebase_admin
from firebase_admin import credentials, firestore, storage
import pyqrcode
from io import BytesIO
import base64
from fastapi.middleware.cors import CORSMiddleware
from urllib.parse import quote
from pydantic import BaseModel
from datetime import datetime


# dev run command - uvicorn main:app --host 127.0.0.1 --port 8500 --reload

app = FastAPI()

# Allow all origins, methods, and headers
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Initialize Firebase Admin SDK
cred = credentials.Certificate("cred.json")
firebase_admin.initialize_app(cred, {"storageBucket": "digitalpraveshpranalli.appspot.com"})
db = firestore.client()
bucket = storage.bucket()



# Endpoint to generate QR code
@app.post("/generateQR")
async def generateQR(placeName: str = Body(..., embed=True)):
    try:
        # Generate QR code
        qr = pyqrcode.create(placeName)
        qr_img = qr.png_as_base64_str(scale=25)
        
        # Save QR image to Firebase Storage
        qr_image_bytes = BytesIO(base64.b64decode(qr_img))
        blob = bucket.blob(f"qr_codes/{placeName}.png")
        blob.upload_from_file(qr_image_bytes, content_type='image/png')

        url = "https://firebasestorage.googleapis.com/v0/b/digitalpraveshpranalli.appspot.com/o/qr_codes%2F"
        download_url = f"{url}{quote(placeName)}.png?alt=media"
        

        return {"success": True, 'qr_code_url': download_url}
    except Exception as e:
        return {"success": False, "error": str(e)}
    

# Endpoint to verify user
class Verify(BaseModel):
    person: str
    place: str

@app.post("/verify")
async def verify(verify: Verify):
    person = verify.person
    place = verify.place

    # Get the current date
    current_date = datetime.now().date()
    formatted_date = current_date.strftime("%Y-%m-%d")

    # Check if the person has a ticket for the today's date
    tickets_ref = db.collection("tickets")
    query = tickets_ref.where("person", "==", person).where("place", "==", place).where("date", "==", formatted_date).limit(1)
    docs = query.stream()

    # Check if any ticket exists for the person at the specified place and date
    if len(list(docs)) > 0:
        return True
    
    return False
