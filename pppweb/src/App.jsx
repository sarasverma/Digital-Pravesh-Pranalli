import { useContext } from "react";
import "./App.css";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { BrowserRouter, Route, Routes, Navigate } from "react-router-dom";
import Home from "./pages/Home";
import Edit from "./pages/Edit";
import About from "./pages/About";
import Auth from "./pages/Auth";
import PageNotFound from "./pages/PageNotFound";
import { AuthContext } from "./context/AuthContext";
import Navbar from "./Components/Navbar";

function App() {
  const { currentUser } = useContext(AuthContext);

  const ProtectedRoute = ({ children }) => {
    if (!currentUser) {
      toast.info("Authenticate to continue");
      return <Navigate to="/auth" />;
    }
    return children;
  };

  return (
    <>
      {/* Routes  */}
      <BrowserRouter>
        <Navbar />
        <Routes>
          <Route
            exact
            path="/"
            element={
              <ProtectedRoute>
                <Home />
              </ProtectedRoute>
            }
          />
          <Route
            exact
            path="/edit"
            element={
              <ProtectedRoute>
                <Edit />
              </ProtectedRoute>
            }
          />
          <Route exact path="/about" element={<About />} />
          <Route exact path="/auth" element={<Auth />} />
          <Route path="*" element={<PageNotFound />} />
        </Routes>
      </BrowserRouter>

      {/* Toast container  */}
      <ToastContainer
        position="top-right"
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="dark"
        transition:Bounce
      />
    </>
  );
}

export default App;
