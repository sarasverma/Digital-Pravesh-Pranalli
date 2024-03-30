import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import { getAuth } from "firebase/auth";
import { getFirestore } from "firebase/firestore";
import { getStorage } from "firebase/storage";

const firebaseConfig = {
  apiKey: import.meta.env.VITE_FIREBASE_API,
  authDomain: "digitalpraveshpranalli.firebaseapp.com",
  projectId: "digitalpraveshpranalli",
  storageBucket: "digitalpraveshpranalli.appspot.com",
  messagingSenderId: "460564518146",
  appId: import.meta.env.VITE_FIREBASE_APPID,
  measurementId: "G-FC2RPN02ZM",
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);

// required stuff
export const auth = getAuth();
export const db = getFirestore();
export const storage = getStorage();
