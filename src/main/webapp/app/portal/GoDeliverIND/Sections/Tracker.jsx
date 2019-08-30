import React from "react";
import withStyles from "@material-ui/core/styles/withStyles";
import Slide from "@material-ui/core/Slide";
import IconButton from "@material-ui/core/IconButton";
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import InputLabel from "@material-ui/core/InputLabel";
import FormControl from "@material-ui/core/FormControl";
import Tooltip from "@material-ui/core/Tooltip";
import Popover from "@material-ui/core/Popover";
import LibraryBooks from "@material-ui/icons/LibraryBooks";
import Close from "@material-ui/icons/Close";
import GridContainer from "app/portal/Components/Grid/GridContainer.jsx";
import GridItem from "app/portal/Components/Grid/GridItem.jsx";
import Button from "app/portal/Components/CustomButtons/Button.jsx";
import javascriptStyles from "app/assets/jss/material-kit-react/views/componentsSections/javascriptStyles.jsx";
import { Card } from 'reactstrap';
import { ToastContainer, ToastPosition, toast } from 'react-toastify';
import ErrorBoundary from 'app/shared/error/error-boundary';
import { Provider } from 'react-redux';
import Home from 'app/modules/home/home';
import initStore from 'app/config/store';

function Transition(props) {
    return <Slide direction="down" {...props} />;
}

class Tracker extends React.Component {
    anchorElLeft = null;
    anchorElTop = null;
    anchorElBottom = null;
    anchorElRight = null;
    constructor(props) {
        super(props);
        const store = initStore();
        this.state = {
            classicModal: true,
            openLeft: false,
            openTop: false,
            openBottom: false,
            openRight: false,
            store: store
        };
    }
    handleClickOpen(modal) {
        var x = [];
        x[modal] = true;
        this.setState(x);
    }
    handleClose(modal) {
        var x = [];
        x[modal] = false;
        this.setState(x);
        this.props.onModalClose();
    }
    handleClosePopover(state) {
        this.setState({
            [state]: false
        });
    }
    handleClickButton(state) {
        this.setState({
            [state]: true
        });
    }
    render() {
        const { classes } = this.props;
        return (

            <Dialog
                classes={{
                    root: classes.center,
                    paper: classes.modal
                }}
                //open={this.state.classicModal}
                open={this.props.parentState}
                TransitionComponent={Transition}
                keepMounted
                onClose={() => this.handleClose("classicModal")}
                aria-labelledby="classic-modal-slide-title"
                aria-describedby="classic-modal-slide-description"
            >
                <DialogTitle
                    id="classic-modal-slide-title"
                    disableTypography
                    className={classes.modalHeader}
                >
                    <IconButton
                        className={classes.modalCloseButton}
                        key="close"
                        aria-label="Close"
                        color="inherit"
                        onClick={() => this.handleClose("classicModal")}
                    >
                        <Close className={classes.modalClose} />
                    </IconButton>
                    <h4 className={classes.modalTitle}>GoDeliverIND Traker</h4>
                </DialogTitle>
                <DialogContent
                    id="classic-modal-slide-description"
                    className={classes.modalBody}
                >
                    <ErrorBoundary>
                        <Provider store={this.state.store}>


                            <div className="app-container">
                                <ToastContainer
                                    position={toast.POSITION.TOP_LEFT}
                                    className="toastify-container"
                                    toastClassName="toastify-toast"
                                />
                                <div className="container-fluid view-container" id="app-view-container">
                                    <Card className="jh-card">
                                        <ErrorBoundary>
                                            <Home vendorId={3}/>
                                        </ErrorBoundary>
                                    </Card>
                                </div>
                            </div>
                        </Provider>
                    </ErrorBoundary>
                </DialogContent>
                <DialogActions className={classes.modalFooter}>
                    <Button
                        onClick={() => this.handleClose("classicModal")}
                        simple
                        className={classes.modalCloseButton1}
                    >
                        Close
                      </Button>
                </DialogActions>
            </Dialog>

        );
    }
}

export default withStyles(javascriptStyles)(Tracker);
