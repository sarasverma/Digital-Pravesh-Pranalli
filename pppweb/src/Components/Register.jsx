import React, { useState } from "react";
import { FaUser } from "react-icons/fa";
import { SiMinutemailer } from "react-icons/si";
import { TbPassword } from "react-icons/tb";
import { createUserWithEmailAndPassword, updateProfile } from "firebase/auth";
import { auth, db } from "../firebase";
import { doc, setDoc } from "firebase/firestore";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import Loader from "./Loader";

const Register = ({ toogleForms }) => {
  const navigate = useNavigate();
  const [registerInfo, setRegisterInfo] = useState({
    name: "",
    email: "",
    password: "",
  });
  const [loading, setLoading] = useState(false);

  const onChangeRegister = (e) => {
    setRegisterInfo({ ...registerInfo, [e.target.name]: e.target.value });
  };

  const handleRegister = async (e) => {
    e.preventDefault();

    const { name, email, password } = registerInfo;

    try {
      const { user } = await createUserWithEmailAndPassword(
        auth,
        email,
        password
      );

      await updateProfile(user, {
        displayName: name,
      });

      // userInfo
      await setDoc(doc(db, "users", email), {
        email,
        name,
        admin: true,
        place: "",
      });

      toast.success(`Welcome ${user.displayName} 😊.`);
      navigate("/");
    } catch (e) {
      toast.error(`Error : ${e}`);
    }
  };

  return (
    <>
      {loading ? (
        <Loader />
      ) : (
        <form
          onSubmit={handleRegister}
          className="py-10 px-5 w-[500px] flex flex-col gap-6 rounded-xl max-sm:w-[100%] text-white text-xl bg-orange-200  bg-clip-padding backdrop-filter backdrop-blur-md bg-opacity-20 border border-gray-100"
        >
          <div className="info text-center">
            <h1 className="text-center">Welcome 🐘</h1>
            <p className="text-sm">
              <span>Already have account? </span>
              <button
                className="text-sky-500"
                onClick={(e) => {
                  e.preventDefault();
                  toogleForms();
                }}
              >
                Login
              </button>
            </p>
          </div>
          <div className="relative">
            <FaUser className="absolute top-[15px] left-[10px]" />
            <input
              type="text"
              placeholder="Name"
              name="name"
              onChange={onChangeRegister}
              autoComplete="on"
              required
              className="px-10 py-2.5 w-full bg-transparent focus:border-b-2 border-orange-500 outline-none placeholder:text-white"
            />
          </div>
          <div className="relative">
            <SiMinutemailer className="absolute top-[15px] left-[10px]" />
            <input
              type="email"
              placeholder="Email"
              name="email"
              onChange={onChangeRegister}
              autoComplete="on"
              required
              className="px-10 py-2.5 w-full bg-transparent focus:border-b-2 border-orange-500 outline-none placeholder:text-white"
            />
          </div>
          <div className="relative">
            <TbPassword className="absolute top-[15px] left-[10px]" />
            <input
              type="password"
              placeholder="Password"
              name="password"
              onChange={onChangeRegister}
              autoComplete="on"
              required
              className="px-10 py-2.5 w-full bg-transparent focus:border-b-2 border-orange-500 outline-none placeholder:text-white"
            />
          </div>
          <div>
            <button className="py-2.5 w-full bg-orange-500 rounded-xl">
              Register
            </button>
          </div>
        </form>
      )}
    </>
  );
};

export default Register;
