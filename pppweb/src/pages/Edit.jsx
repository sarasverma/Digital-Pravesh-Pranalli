import React, { useContext, useEffect, useState } from "react";
import Form from "../Components/Form";
import { doc, getDoc, setDoc, updateDoc } from "firebase/firestore";
import { db, storage } from "../firebase";
import Loader from "../Components/Loader";
import { AuthContext } from "../context/AuthContext";
import { getDownloadURL, ref, uploadBytes } from "firebase/storage";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";

const Edit = () => {
  const [formData, setFormData] = useState({
    placeName: "",
    location: "",
    description: "",
    openingDays: "",
    openingHours: "",
    entryFees: "",
    contactInfo: {
      phoneNumber: "",
      email: "",
      website: "",
    },
    facilities: {
      restrooms: false,
      parking: false,
      wheelchairAccessible: false,
      guidedTours: false,
      souvenirShop: false,
      cafeteria: false,
    },
    additionalInfo: {
      nearbyAttractions: "",
      visitorTips: "",
      restrictions: "",
    },
    images: [],
  });
  const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(false);
  const [firstTime, setFirstTime] = useState(true);

  const { currentUser } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validation logic
    const validationErrors = {};
    if (!formData.placeName.trim()) {
      validationErrors.placeName = "Place Name is required";
    }
    if (!formData.location.trim()) {
      validationErrors.location = "Location is required";
    }
    if (!formData.description.trim()) {
      validationErrors.description = "Description is required";
    }
    if (!formData.openingDays.trim()) {
      validationErrors.openingDays = "Opening Days is required";
    }
    if (!formData.openingHours.trim()) {
      validationErrors.openingHours = "Opening Hours is required";
    }
    if (!formData.entryFees.trim()) {
      validationErrors.entryFees = "Entry Fees is required";
    }
    if (!formData.contactInfo.phoneNumber.trim()) {
      validationErrors["phoneNumber"] = "Phone Number is required";
    }
    if (!formData.contactInfo.email.trim()) {
      validationErrors["email"] = "Email is required";
    }
    if (!formData.contactInfo.website.trim()) {
      validationErrors["website"] = "Website is required";
    }

    if (Object.keys(validationErrors).length === 0) {
      // Form is valid, proceed with submission

      setLoading(true);

      // save to firestore
      // if first time, create a new document
      if (firstTime) {
        let imageUrls = [];
        const { images } = formData;

        const storageRef = ref(storage, formData.placeName);

        if (formData.images.length !== 0) {
          // upload images to storage
          imageUrls = await Promise.all(
            images.map(async (image) => {
              const imageRef = ref(storageRef, image.name);
              await uploadBytes(imageRef, image);
              return getDownloadURL(imageRef);
            })
          );
        }

        const url = `${import.meta.env.VITE_BACKEND_URL}/generateQR`;

        const response = await fetch(url, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ placeName: formData.placeName }), // assuming formData is defined elsewhere
        });

        const qr = await response.json();

        // generate qr code
        await setDoc(doc(db, "places", formData.placeName), {
          ...formData,
          images: imageUrls,
          qrUrl: qr.qr_code_url || "",
        });

        await updateDoc(doc(db, "users", currentUser.email), {
          place: formData.placeName,
        });
      } else {
        // update the existing document

        let imageUrls = [];
        const { images } = formData;

        const storageRef = ref(storage, formData.placeName);

        if (formData.images.length !== 0) {
          // upload images to storage
          imageUrls = await Promise.all(
            images.map(async (image) => {
              const imageRef = ref(storageRef, image.name);
              await uploadBytes(imageRef, image);
              return getDownloadURL(imageRef);
            })
          );
        }
        await updateDoc(doc(db, "places", formData.placeName), {
          ...formData,
          images: imageUrls,
        });
      }

      setLoading(false);
      toast.success("Place details saved successfully ✅");
      navigate("/");
    } else {
      // Set validation errors
      setErrors(validationErrors);
    }
  };

  const init = async () => {
    setLoading(true);
    const res = await getDoc(doc(db, "users", currentUser.email));
    if (res.data().place !== "") {
      // do something
      setFirstTime(false);

      const response = await getDoc(doc(db, "places", res.data().place));

      setFormData(response.data());
    } else {
      setFirstTime(true);
    }

    setLoading(false);
  };

  useEffect(() => {
    init();
  }, []);

  return (
    <div>
      {loading ? (
        <Loader />
      ) : (
        <Form
          firstTime={firstTime}
          formData={formData}
          setFormData={setFormData}
          errors={errors}
          handleSubmit={handleSubmit}
        />
      )}
    </div>
  );
};

export default Edit;
