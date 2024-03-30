import React, { useState } from "react";
import SwipeableViews from "react-swipeable-views";
import { autoPlay } from "react-swipeable-views-utils";
import { useTheme, Box, MobileStepper } from "@material-ui/core";

const AutoPlaySwipeableViews = autoPlay(SwipeableViews);

function Carousel({ images }) {
  const theme = useTheme();
  const [activeStep, setActiveStep] = useState(0);
  const maxSteps = images.length;

  const handleStepChange = (step) => {
    setActiveStep(step);
  };

  return (
    <Box>
      <AutoPlaySwipeableViews
        axis={theme.direction === "rtl" ? "x-reverse" : "x"}
        index={activeStep}
        onChangeIndex={handleStepChange}
        enableMouseEvents
      >
        {images.map((step, index) => (
          <div key={index}>
            {Math.abs(activeStep - index) <= 2 ? (
              <Box
                component="img"
                style={{
                  height: "450px",
                  display: "block",
                  overflow: "hidden",
                  width: "100%",
                  objectFit: "cover",
                }}
                src={step}
                alt={index}
              />
            ) : null}
          </div>
        ))}
      </AutoPlaySwipeableViews>

      <div className="flex justify-center transform -translate-y-12 bg-transparent">
        <MobileStepper
          steps={maxSteps}
          position="static"
          activeStep={activeStep}
          style={{ backgroundColor: "transparent", gap: "25px" }}
          classes={{ dots: "gap-8" }}
        />
      </div>
    </Box>
  );
}

export default Carousel;
