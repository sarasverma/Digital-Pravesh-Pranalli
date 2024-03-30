import React, { useContext, useRef } from "react";
import { ImCross } from "react-icons/im";
import { GiHamburgerMenu } from "react-icons/gi";
import { Link, useLocation } from "react-router-dom";
import { signOut } from "firebase/auth";
import { auth } from "../firebase";
import { AuthContext } from "../context/AuthContext";
import { toast } from "react-toastify";
import { GiIndiaGate } from "react-icons/gi";

const Navbar = () => {
  const location = useLocation();
  const navLinks = useRef(null);

  const { currentUser } = useContext(AuthContext);

  const handleCloseNav = () => {
    if (!navLinks.current.classList.contains("max-sm:translate-x-[-100vw]"))
      navLinks.current.classList.add("max-sm:translate-x-[-100vw]");
  };

  const handleSignOut = () => {
    signOut(auth);
    toast.success("Logout successfully !");
  };

  if (location.pathname === "/auth") return <></>;

  return (
    <nav className="flex justify-between items-center h-16 px-16 max-sm:px-8 sticky bg-orange-500 z-50 text-white">
      <div className="logo hover:scale-125">
        <Link to="/">
          <div
            className="w-full aspect-square animate-bounce"
            style={{ scale: "2.3" }}
          >
            <GiIndiaGate />
          </div>
        </Link>
      </div>
      <div
        ref={navLinks}
        className="nav-links max-sm:fixed max-sm:top-0 max-sm:left-0 max-sm:w-[100%] max-sm:h-[100%] max-sm:grid max-sm:place-items-center transition-[transform] duration-[0.5s] max-sm:translate-x-[-100vw] max-sm:bg-orange-500"
      >
        <ul className="flex gap-5 max-sm:flex-col max-sm:items-center max-sm:justify-center">
          <li className="hover:scale-125">
            <Link to="/" onClick={handleCloseNav} className="max-sm:text-2xl">
              Home
            </Link>
          </li>
          <li className="hover:scale-125">
            <Link
              to="/edit"
              onClick={handleCloseNav}
              className="max-sm:text-2xl"
            >
              Edit
            </Link>
          </li>
          <li className="hover:scale-125">
            <Link
              to="/about"
              onClick={handleCloseNav}
              className="max-sm:text-2xl"
            >
              About
            </Link>
          </li>
          {currentUser === null ? (
            <li className="hover:scale-125">
              <Link to="/auth" className="max-sm:text-2xl">
                Login
              </Link>
            </li>
          ) : (
            <li className="hover:scale-125">
              <button
                onClick={() => {
                  handleSignOut();
                  handleCloseNav();
                }}
                className="max-sm:text-2xl"
              >
                LogOut
              </button>
            </li>
          )}
        </ul>
        <button
          className="hidden max-sm:block max-sm:absolute top-10 right-10"
          onClick={handleCloseNav}
        >
          <ImCross />
        </button>
      </div>
      <div className="toogleNav hidden max-sm:block">
        <button
          className="max-sm:block"
          onClick={() => {
            if (
              navLinks.current.classList.contains("max-sm:translate-x-[-100vw]")
            )
              navLinks.current.classList.remove("max-sm:translate-x-[-100vw]");
          }}
        >
          <GiHamburgerMenu />
        </button>
      </div>
    </nav>
  );
};

export default Navbar;
