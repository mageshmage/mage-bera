import React from "react";
import withStyles from "@material-ui/core/styles/withStyles";
import GridContainer from "app/portal/Components/Grid/GridContainer.jsx";
import GridItem from "app/portal/Components/Grid/GridItem.jsx";
import Button from "app/portal/Components/CustomButtons/Button.jsx";
import downloadStyle from "./workStyle.jsx";

class ContactUs extends React.Component {
    render() {
        const { classes } = this.props;
        return (

            <div className={classes.section}>
                <div className={classes.container}>
                    {/*<GridContainer className={classes.textCenter} justify="center">
                        <GridItem xs={12} sm={12} md={8}>
                            <h4 className={classes.description}>
                                Ready to start your service with us? That's great! Give us a call or send us an email and we will get back to you as soon as possible!
              </h4>
                        </GridItem>
                    </GridContainer>

                    <div className={classes.textCenter}>
                        <Button color="success" href=""
                            target="_blank">
                            <i className={classes.socials + " fas fa-phone"} /> 91-9500302626
                </Button>
                        <Button color="twitter" href=""
                            target="_blank">
                            <i className={classes.socials + " fas fa-envelope-open"} /> mail@godeliverind.com
                </Button>
        </div>*/}
                    <div className={classes.textCenter}>

                        <Button color="primary" href="https://www.linkedin.com/in/godeliver-ind-bb627318b/"
                            target="_blank">
                            <i className={classes.socials + " fab fa-linkedin"} /> Linkedin
            </Button>
                        <Button color="twitter" href="https://twitter.com/DeliverInd"
                            target="_blank">
                            <i className={classes.socials + " fab fa-twitter"} /> Tweet
            </Button>
                        <Button color="facebook" href="https://www.facebook.com/GoDeliver-IND-100555787962085"
                            target="_blank">
                            <i className={classes.socials + " fab fa-facebook-square"} />{" "}
                            Share
            </Button>
                        <Button color="success" href="http://godeliverind.in"
                            target="_blank">
                            <i className={classes.socials + " fab fa-whatsapp"} />
                            Whatsapp
            </Button>
                        <Button color="github" href="https://www.instagram.com/go_deliver_ncr/"
                            target="_blank">
                            <i className={classes.socials + " fab fa-instagram"} /> Instagram
            </Button>
                        <GridContainer justify="center">
                            <h3 className={classes.title}>Thank you for supporting us!</h3>
                        </GridContainer>
                    </div>
                </div>
            </div>


        );
    }
}

export default withStyles(downloadStyle)(ContactUs);
