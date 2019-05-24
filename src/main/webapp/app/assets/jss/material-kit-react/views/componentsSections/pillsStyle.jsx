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
    lineHeight: "1.5em"
  },
  h4: {
    color: "#3c4858",
    fontSize: "18px",
    lineHeight: "25px",
    marginBottom: "10px",
    fontStyle: "normal"
  }
};

export default pillsStyle;
