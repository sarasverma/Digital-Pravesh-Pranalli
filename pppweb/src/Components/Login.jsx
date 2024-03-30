import React, { useState } from "react";
import { SiMinutemailer } from "react-icons/si";
import { TbPassword } from "react-icons/tb";
import { signInWithEmailAndPassword } from "firebase/auth";
import { auth } from "../firebase";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import Loader from "./Loader";

const Login = ({ toogleForms }) => {
  const navigate = useNavigate();
  const [loginInfo, setLoginInfo] = useState({ email: "", password: "" });
  const [loading, setLoading] = useState(false);

  const onChangeLogin = (e) => {
    setLoginInfo({ ...loginInfo, [e.target.name]: e.target.value });
  };

  const handleLogin = async (e) => {
    e.preventDefault();

    const { email, password } = loginInfo;
    setLoading(true);
    try {
      const { user } = await signInWithEmailAndPassword(auth, email, password);

      setLoading(false);
      toast.success(`Welcome back ${user.displayName} 😊`);
      navigate("/");
    } catch (e) {
      setLoading(false);
      toast.error(`Error authenticating user: ${e}`);
    }
  };

  return (
    <>
      {loading ? (
        <Loader />
      ) : (
        <form
          onSubmit={handleLogin}
          className="py-10 px-5 w-[500px] flex flex-col gap-6 rounded-xl max-sm:w-[100%] text-white text-xl bg-orange-200  bg-clip-padding backdrop-filter backdrop-blur-md bg-opacity-20 border border-gray-100"
        >
          <div className="info text-center">
            <h1 className="text-center">Welcome 🐘</h1>
            <p className="text-sm">
              <span>Don't have a account? </span>
              <button
                className="text-sky-500"
                onClick={(e) => {
                  e.preventDefault();
                  toogleForms();
                }}
              >
                {" "}
                Register
              </button>
            </p>
          </div>
          <div className="relative">
            <SiMinutemailer className="absolute top-[15px] left-[10px]" />
            <input
              type="email"
              placeholder="Email"
              name="email"
              onChange={onChangeLogin}
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
              onChange={onChangeLogin}
              autoComplete="on"
              required
              className="px-10 py-2.5 w-full bg-transparent focus:border-b-2 border-orange-500 outline-none placeholder:text-white"
            />
          </div>
          <div>
            <button className="py-2.5 w-full bg-orange-500 rounded-xl">
              Login
            </button>
          </div>
        </form>
      )}
    </>
  );
};

export default Login;
