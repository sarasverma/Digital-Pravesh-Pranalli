import React from "react";
import RiseLoader from "react-spinners/RiseLoader";

const Loader = ({ loading }) => {
  return (
    <div className="w-full flex justify-center mt-14">
      <RiseLoader
        color="#fb923c"
        loading={loading}
        size={70}
        aria-label="Loading Spinner"
        data-testid="loader"
        className="mt-4"
      />
    </div>
  );
};

export default Loader;
