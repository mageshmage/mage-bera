import { container } from "app/assets/jss/material-kit-react.jsx";

const footerStyle = {
  block: {
    color: "inherit",
    padding: "0.9375rem",
    fontWeight: "500",
    fontSize: "12px",
    textTransform: "uppercase",
    borderRadius: "3px",
    textDecoration: "none",
    position: "relative",
    display: "block"
  },
  left: {
    float: "left!important",
    display: "block"
  },
  right: {
    padding: "15px 0",
    margin: "0",
    float: "right!important"
  },
  footer: {
    float: "left",
    width: "100%",
    padding: "30px 0px 0",
    background: "#ffffff",
    color: "#003366"
  },
  thirty: {
    float: "left",
    width: "30%"
  },
  seventy: {
    float: "left",
    width: "70%"
  },
  qlinks: {
    float: "left",
    width: "100%"
  },
  oneFourth: {
    position: "relative",
    marginRight: "4%",
    float: "left",
    width: "20%"
  },
  oneFifth: {
    position: "relative",
    marginRight: "4%",
    float: "left",
    width: "40%"
  },
  logo: {
    borderRadius: "50% !important",
    maxWidth: "100%",
    height: "auto !important"
  },
  footerBottom: {
    background: "#003366",
    padding: "5px 20px",
    width: "100%",
    color: "#fff",
    float: "left",
    fontSize: "20px",
    marginTop: "30px",
    textAlign: "center"
  },

  h4:{
    fontSize: "22px",
    color: "#003366",
    fontWeight: "bold"
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
    fontWeight: "800"
  },

  a: {
    color: "#000000",
    textDecoration: "none",
    backgroundColor: "transparent"
  },
  footerWhiteFont: {
    "&,&:hover,&:focus": {
      color: "#FFFFFF"
    }
  },
  container,
  list: {
    marginBottom: "0",
    padding: "0",
    marginTop: "0"
  },
  inlineBlock: {
    display: "inline-block",
    padding: "0px",
    width: "auto"
  },
  icon: {
    width: "18px",
    height: "18px",
    position: "relative",
    top: "3px",
    color: "red"
  }
};
export default footerStyle;
