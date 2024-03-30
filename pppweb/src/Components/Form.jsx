import React, { useState } from "react";
import {
  TextField,
  Checkbox,
  FormControlLabel,
  Button,
  Typography,
  Grid,
  Container,
  Box,
} from "@material-ui/core";

const Form = ({ firstTime, formData, setFormData, errors, handleSubmit }) => {
  const [imagePreviews, setImagePreviews] = useState(formData.images || []);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleContactChange = (event) => {
    const { name, value } = event.target;
    setFormData((prevState) => ({
      ...prevState,
      contactInfo: { ...prevState.contactInfo, [name]: value },
    }));
  };

  const handleAdditionalChange = (event) => {
    const { name, value } = event.target;
    setFormData((prevState) => ({
      ...prevState,
      additionalInfo: { ...prevState.additionalInfo, [name]: value },
    }));
  };

  const handleCheckboxChange = (event) => {
    const { name, checked } = event.target;
    setFormData((prevState) => ({
      ...prevState,
      facilities: {
        ...prevState.facilities,
        [name]: checked,
      },
    }));
  };

  const handleImageChange = (event) => {
    const files = Array.from(event.target.files);
    setFormData((prevState) => ({
      ...prevState,
      images: files,
    }));

    const previews = files.map((file) => URL.createObjectURL(file));
    setImagePreviews(previews);
  };

  return (
    <form onSubmit={handleSubmit}>
      <Container className="md:m-10 sm:m-0">
        <Box my={2} className="my-2 mb-2">
          <Typography variant="h6">Place Information</Typography>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}>
              <TextField
                name="placeName"
                label="Place Name"
                value={formData.placeName}
                onChange={handleChange}
                error={!!errors.placeName}
                helperText={errors.placeName}
                fullWidth
                disabled={!firstTime}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                name="location"
                label="Location"
                value={formData.location}
                onChange={handleChange}
                error={!!errors.location}
                helperText={errors.location}
                fullWidth
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                name="description"
                label="Description"
                value={formData.description}
                onChange={handleChange}
                error={!!errors.description}
                helperText={errors.description}
                fullWidth
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                type="file"
                name="images"
                label="Images"
                onChange={handleImageChange}
                fullWidth
                inputProps={{ multiple: true, accept: "image/*" }}
                className="mb-4"
                error={!!errors.images}
                helperText={errors.images}
              />
            </Grid>
            <Grid item xs={12}>
              <Box className="flex flex-wrap gap-2">
                {imagePreviews.map((preview, index) => (
                  <img
                    key={index}
                    src={preview}
                    alt={`Image preview ${index + 1}`}
                    className="w-32 h-32 object-cover"
                  />
                ))}
              </Box>
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                name="openingDays"
                label="Opening Days"
                value={formData.openingDays}
                onChange={handleChange}
                error={!!errors.openingDays}
                helperText={errors.openingDays}
                fullWidth
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                name="openingHours"
                label="Opening Hours"
                value={formData.openingHours}
                onChange={handleChange}
                error={!!errors.openingHours}
                helperText={errors.openingHours}
                fullWidth
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                name="entryFees"
                label="Entry Fees"
                value={formData.entryFees}
                onChange={handleChange}
                error={!!errors.entryFees}
                helperText={errors.entryFees}
                fullWidth
              />
            </Grid>
          </Grid>
        </Box>

        <Box my={2} className="my-2 mb-2">
          <Typography variant="h6">Contact Information</Typography>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={4}>
              <TextField
                name="phoneNumber"
                label="Phone Number"
                value={formData.contactInfo.phoneNumber}
                onChange={handleContactChange}
                error={!!errors.phoneNumber}
                helperText={errors.phoneNumber}
                fullWidth
              />
            </Grid>
            <Grid item xs={12} sm={4}>
              <TextField
                name="email"
                label="Email"
                value={formData.contactInfo.email}
                onChange={handleContactChange}
                error={!!errors.email}
                helperText={errors.email}
                fullWidth
              />
            </Grid>
            <Grid item xs={12} sm={4}>
              <TextField
                name="website"
                label="Website"
                value={formData.contactInfo.website}
                onChange={handleContactChange}
                error={!!errors.website}
                helperText={errors.website}
                fullWidth
              />
            </Grid>
          </Grid>
        </Box>

        <Box my={2} className="my-2 py-2">
          <Typography variant="h6">Facilities</Typography>
          <Grid container spacing={2}>
            {Object.entries(formData.facilities).map(([key, value]) => (
              <Grid item xs={6} sm={4} key={key}>
                <FormControlLabel
                  control={
                    <Checkbox
                      checked={value}
                      onChange={handleCheckboxChange}
                      name={key}
                    />
                  }
                  label={key}
                />
              </Grid>
            ))}
          </Grid>
        </Box>

        <Box my={2} className="my-2 mb-2">
          <Typography variant="h6">Additional Information</Typography>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <TextField
                name="nearbyAttractions"
                label="Nearby Attractions"
                value={formData.additionalInfo.nearbyAttractions}
                onChange={handleAdditionalChange}
                fullWidth
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                name="visitorTips"
                label="Visitor Tips"
                value={formData.additionalInfo.visitorTips}
                onChange={handleAdditionalChange}
                fullWidth
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                name="restrictions"
                label="Restrictions"
                value={formData.additionalInfo.restrictions}
                onChange={handleAdditionalChange}
                fullWidth
              />
            </Grid>
          </Grid>
        </Box>

        <Button
          type="submit"
          variant="contained"
          color="primary"
          className="my-2 max-sm:w-full float-right"
        >
          Submit
        </Button>
      </Container>
    </form>
  );
};

export default Form;
