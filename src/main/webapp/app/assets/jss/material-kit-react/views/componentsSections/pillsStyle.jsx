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
    textDecoration: "none"
  },
  ...imagesStyle,
  paragrap: {
    color: "#3c4858",
    fontWeight: "300",
    lineHeight: "1.5em",
    fontSize: "16px"
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
    marginLeft: "30%"
  }
};

export default pillsStyle;
