import { container, title } from "app/assets/jss/material-kit-react.jsx";

import imagesStyle from "app/assets/jss/material-kit-react/imagesStyles.jsx";

const pillsStyle = {
  section: {
    padding: "15px 0"
  },
  container,
  title: {
    ...title,
    marginTop: "30px",
    minHeight: "32px",
    textDecoration: "none",
    marginLeft: "10px"
  },
  ...imagesStyle,
  paragrap: {
    color: "#000000",
    fontWeight: "300",
    //fontFamily: "none",
    lineHeight: "1.5em",
    fontSize: "16px",
    textAlign: "justify"
  },
  h4: {
    color: "#3c4858",
    fontSize: "18px",
    lineHeight: "25px",
    marginBottom: "10px",
    fontStyle: "normal",
    fontWeight: "700"
  },
  margin30: {
    marginLeft: "20%"
  },
  goBold: {
    fontWeight: "bold"
  },
  marginAlignment: {
    marginRight: "40px",
    marginLeft: "40px"
  }
};

export default pillsStyle;
