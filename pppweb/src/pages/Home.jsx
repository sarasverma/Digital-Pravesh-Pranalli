import React, { useContext, useEffect, useState } from "react";
import Loader from "../Components/Loader";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";
import {
  collection,
  doc,
  getDoc,
  getDocs,
  query,
  where,
} from "firebase/firestore";
import { db } from "../firebase";
import { toast } from "react-toastify";
import Carousel from "../Components/Carousel";
import CountUp from "react-countup";
import { Container, Typography } from "@mui/material";
import { set } from "mongoose";

const Home = () => {
  const [loading, setLoading] = useState(false);
  const [place, setPlace] = useState({});
  const [visitors, setVisitors] = useState(100);

  const navigate = useNavigate();
  const { currentUser } = useContext(AuthContext);

  const init = async () => {
    setLoading(true);
    const res = await getDoc(doc(db, "users", currentUser.email));

    // console.log(res.data());
    if (res.data().place !== "") {
      // do something
      const response = await getDoc(doc(db, "places", res.data().place));
      setPlace(response.data());

      // TODO : Check ticket count
      const today = new Date();
      const formattedDate = today.toLocaleDateString("en-GB"); // 'dd/mm/yyyy' format

      const querySnapshot = await getDocs(
        query(
          collection(db, "tickets"),
          where("place", "==", res.data().place),
          where("date", "==", formattedDate)
        )
      );
      setVisitors(querySnapshot.size);
    } else {
      setPlace({});
      toast.warning("Add the monument details first 🤷‍♂️");
      navigate("/edit");
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
        <>
          <Carousel images={place.images || []} />

          <Container className="md:m-10 sm:m-0">
            <div className="counter flex justify-center flex-wrap sm:gap-[300px]  mb-16">
              <div className="flex flex-col justify-center items-center">
                <Typography variant="h5">Today</Typography>
                <Typography variant="h2">
                  <CountUp end={new Date().getDate()} duration={5} />
                  /
                  <CountUp end={new Date().getMonth() + 1} duration={5} />
                  /
                  <CountUp
                    end={new Date().getFullYear()}
                    duration={5}
                    separator=""
                  />
                </Typography>
              </div>
              <div className="flex flex-col justify-center items-center">
                <Typography variant="h5">Expected Visitors</Typography>
                <Typography variant="h2">
                  <CountUp end={visitors || 100} duration={5} />
                </Typography>
              </div>
            </div>

            <div className="qr flex justify-center items-center mb-16">
              <img src={place.qrUrl || ""} alt="qrcode" />
            </div>
          </Container>
        </>
      )}
    </div>
  );
};

export default Home;
