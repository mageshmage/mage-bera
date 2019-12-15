import { container, title } from "app/assets/jss/material-kit-react.jsx";

const landingPageStyle = {
  navLink:{
    fontWeight: "800",
    color: "#3C4858",
    background: "#2e6cdc29"
  },
  navLinkWhite:{
    fontWeight: "800",
    color: "#ffffff",
    background: "#2e6cdc29"
  },
  container: {
    zIndex: "12",
    color: "#FFFFFF",
    ...container
  },
  titleAlign: {
    textAlign: "center"
  },
  title: {
    ...title,
    display: "inline-block",
    position: "relative",
    marginTop: "30px",
    minHeight: "32px",
    color: "#FFFFFF",
    textDecoration: "none"
  },
  subtitle: {
    fontSize: "1.313rem",
    maxWidth: "500px",
    margin: "10px auto 0"
  },
  main: {
    background: "#FFFFFF",
    position: "relative",
    zIndex: "3"
  },
  mainRaised: {
    margin: "-70px 30px 0px",
    borderRadius: "6px",
    boxShadow:
      "0 16px 24px 2px rgba(0, 0, 0, 0.14), 0 6px 30px 5px rgba(0, 0, 0, 0.12), 0 8px 10px -5px rgba(0, 0, 0, 0.2)"
  },
  inputRootCustomClasses: {
    margin: "0!important"
  },
  formControl: {
    margin: "0 !important",
    paddingTop: "0"
  },
  searchIcon: {
    width: "20px",
    height: "20px",
    color: "inherit"
  },
  dropdownLink: {
    "&,&:hover,&:focus": {
      color: "inherit",
      textDecoration: "none",
      display: "block",
      padding: "10px 20px"
    }
  },
  ul:{
    float: "left",
    width: "100%",
    padding: "0px",
    margin: "5px 0px 0px 0px",
    listStyle: "none"
  },

  li:{
    padding: "4px 0px",
    margin: "0px",
    listStyle: "none",
    color: "#003366"
  },
  qlinks: {
    float: "left",
    width: "100%"
  },
  divWidth: {
    width: "20px"
  }
};

export default landingPageStyle;
