import { title } from "app/assets/jss/material-kit-react.jsx";

const workStyle = {
  section: {
    padding: "15px 0"
  },
  title: {
    ...title,
    //marginBottom: "50px",
    //marginTop: "30px",
    minHeight: "32px",
    textDecoration: "none",
    textAlign: "center"
  },
  description: {
    color: "#999",
    textAlign: "center",
    color: "#3c4858",
    fontWeight: "300",
    lineheight: "1.5em",
    fontSize: "15px",
    margin: "0 0 10px",
    display: "block",
    fontWeight: "400"
  },
  textCenter: {
    textAlign: "center"
  },
  textArea: {
    marginRight: "15px",
    marginLeft: "15px"
  },
  icons: {
    width: "20px",
    height: "20px",
    marginRight: "3px"
  },
  socialIcons: {
    position: "relative",
    fontSize: "20px !important",
    marginRight: "4px"
  },
  sharingArea: {
    marginTop: "80px"
  },
  socials: {
    maxWidth: "24px",
    marginTop: "0",
    width: "100%",
    transform: "none",
    left: "0",
    top: "0",
    height: "100%",
    fontSize: "20px",
    marginRight: "4px"
  },
  contactbox:{
    boxShadow: "0 2px 2px 0 rgba(156, 39, 176, 0.14), 0 3px 1px -2px rgba(156, 39, 176, 0.2), 0 1px 5px 0 rgba(156, 39, 176, 0.12)",    
    backgroundColor: "#9c27b0",
    color: "#FFFFFF",
    border: "none",
    margin: ".3125rem 1px",
    cursor: "pointer",
    padding: "12px 30px",
    position: "relative",
    minWidth: "auto",
    fontSize: "12px",
    minHeight: "auto",
    boxShadow: "0 2px 2px 0 rgba(153, 153, 153, 0.14), 0 3px 1px -2px rgba(153, 153, 153, 0.2), 0 1px 5px 0 rgba(153, 153, 153, 0.12)",
    transition: "box-shadow 0.2s cubic-bezier(0.4, 0, 1, 1), background-color 0.2s cubic-bezier(0.4, 0, 0.2, 1)",
    textAlign: "center",
    fontWeight: "400",
    lineHeight: "1.42857143",
    whiteSpace: "nowrap",
    touchAction: "manipulation",
    borderRadius: "3px",
    textTransform: "uppercase",
    letterSpacing: "0",
    verticalAlign: "middle",
  },
  typo: {
    //paddingLeft: "25%",
    //marginBottom: "40px",
    position: "relative",
    width: "100%"
  },
  note: {
    fontFamily: '"Roboto", "Helvetica", "Arial", sans-serif',
    bottom: "10px",
    color: "#c0c1c2",
    display: "block",
    fontWeight: "400",
    fontSize: "14px",
    lineHeight: "14px",
    left: "0",
    marginLeft: "20px",
    position: "absolute",
    width: "260px"
  },
  map: {
    paddingTop: "0px",
    paddingBottom: "0px"
  },
  mapLegend:{
    display: "flex",
    width: "100%",
    justifyContent: "center"
  },
  mapItem: {
    listStyle: "none",
    fontSize: "16px",
    padding: "0 15px 10px"
  },
  legendItemGreen: {
    color: "green",
    width: "200px"
  },
  legendItemBlue:{
    color: "#0288d1",
    width: "200px"
  },
  legendItemViolet: {
    color: "#9fa8da",
    width: "200px"
  },
  mapResponsive: {
    overflow: "hidden",
    //paddingBottom: "30.25%",
    position:"relative",
    //height:0
  }
};

export default workStyle;
