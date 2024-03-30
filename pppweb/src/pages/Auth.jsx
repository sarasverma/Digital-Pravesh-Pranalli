import React, { useRef } from "react";
import Register from "../Components/Register";
import Login from "../Components/Login";

const Auth = () => {
  const register = useRef(null);
  const login = useRef(null);

  const bg1 =
    "https://images.unsplash.com/photo-1548013146-72479768bada?q=80&w=1776&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";

  const toogleForms = () => {
    register.current.classList.toggle("hidden");
    login.current.classList.toggle("hidden");
  };

  return (
    <div
      className="grid place-items-center antialiased w-[100dvw] h-[100dvh] bg-no-repeat bg-cover bg-center"
      style={{
        backgroundImage: `url(${bg1})`,
      }}
    >
      <div
        ref={register}
        className="max-sm:px-2 max-sm:w-full transition-[display] duration-[0.5s] hidden"
      >
        <Register toogleForms={toogleForms} />
      </div>

      <div
        ref={login}
        className="max-sm:px-2 max-sm:w-full transition-[display] duration-[0.5s]"
      >
        <Login toogleForms={toogleForms} />
      </div>
    </div>
  );
};

export default Auth;
