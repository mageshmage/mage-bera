/* tslint:disable no-unused-expression */
import { browser, element, by, protractor } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ShipmentTrackingComponentsPage from './shipment-tracking.page-object';
import { ShipmentTrackingDeleteDialog } from './shipment-tracking.page-object';
import ShipmentTrackingUpdatePage from './shipment-tracking-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('ShipmentTracking e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let shipmentTrackingUpdatePage: ShipmentTrackingUpdatePage;
  let shipmentTrackingComponentsPage: ShipmentTrackingComponentsPage;
  let shipmentTrackingDeleteDialog: ShipmentTrackingDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();

    await signInPage.username.sendKeys('admin');
    await signInPage.password.sendKeys('admin');
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
  });

  it('should load ShipmentTrackings', async () => {
    await navBarPage.getEntityPage('shipment-tracking');
    shipmentTrackingComponentsPage = new ShipmentTrackingComponentsPage();
    expect(await shipmentTrackingComponentsPage.getTitle().getText()).to.match(/Shipment Trackings/);
  });

  it('should load create ShipmentTracking page', async () => {
    await shipmentTrackingComponentsPage.clickOnCreateButton();
    shipmentTrackingUpdatePage = new ShipmentTrackingUpdatePage();
    expect(await shipmentTrackingUpdatePage.getPageTitle().getAttribute('id')).to.match(
      /cargotrackerApp.shipmentTracking.home.createOrEditLabel/
    );
  });

  it('should create and save ShipmentTrackings', async () => {
    const nbButtonsBeforeCreate = await shipmentTrackingComponentsPage.countDeleteButtons();

    await shipmentTrackingUpdatePage.setTrackingDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await shipmentTrackingUpdatePage.getTrackingDateInput()).to.contain('2001-01-01T02:30');
    await shipmentTrackingUpdatePage.setActivitiesInput('activities');
    expect(await shipmentTrackingUpdatePage.getActivitiesInput()).to.match(/activities/);
    await shipmentTrackingUpdatePage.shipmentInfoSelectLastOption();
    await waitUntilDisplayed(shipmentTrackingUpdatePage.getSaveButton());
    await shipmentTrackingUpdatePage.save();
    await waitUntilHidden(shipmentTrackingUpdatePage.getSaveButton());
    expect(await shipmentTrackingUpdatePage.getSaveButton().isPresent()).to.be.false;

    await shipmentTrackingComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await shipmentTrackingComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last ShipmentTracking', async () => {
    await shipmentTrackingComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await shipmentTrackingComponentsPage.countDeleteButtons();
    await shipmentTrackingComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    shipmentTrackingDeleteDialog = new ShipmentTrackingDeleteDialog();
    expect(await shipmentTrackingDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /cargotrackerApp.shipmentTracking.delete.question/
    );
    await shipmentTrackingDeleteDialog.clickOnConfirmButton();

    await shipmentTrackingComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await shipmentTrackingComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
