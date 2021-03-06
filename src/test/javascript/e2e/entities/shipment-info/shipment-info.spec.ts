/* tslint:disable no-unused-expression */
import { browser, element, by, protractor } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ShipmentInfoComponentsPage from './shipment-info.page-object';
import { ShipmentInfoDeleteDialog } from './shipment-info.page-object';
import ShipmentInfoUpdatePage from './shipment-info-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('ShipmentInfo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let shipmentInfoUpdatePage: ShipmentInfoUpdatePage;
  let shipmentInfoComponentsPage: ShipmentInfoComponentsPage;
  let shipmentInfoDeleteDialog: ShipmentInfoDeleteDialog;

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

  it('should load ShipmentInfos', async () => {
    await navBarPage.getEntityPage('shipment-info');
    shipmentInfoComponentsPage = new ShipmentInfoComponentsPage();
    expect(await shipmentInfoComponentsPage.getTitle().getText()).to.match(/Shipment Infos/);
  });

  it('should load create ShipmentInfo page', async () => {
    await shipmentInfoComponentsPage.clickOnCreateButton();
    shipmentInfoUpdatePage = new ShipmentInfoUpdatePage();
    expect(await shipmentInfoUpdatePage.getPageTitle().getAttribute('id')).to.match(/cargotrackerApp.shipmentInfo.home.createOrEditLabel/);
  });

  it('should create and save ShipmentInfos', async () => {
    const nbButtonsBeforeCreate = await shipmentInfoComponentsPage.countDeleteButtons();

    await shipmentInfoUpdatePage.setConsignmentNoInput('consignmentNo');
    expect(await shipmentInfoUpdatePage.getConsignmentNoInput()).to.match(/consignmentNo/);
    await shipmentInfoUpdatePage.setBookingDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await shipmentInfoUpdatePage.getBookingDateInput()).to.contain('2001-01-01T02:30');
    await shipmentInfoUpdatePage.setExpectedDeliveryDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await shipmentInfoUpdatePage.getExpectedDeliveryDateInput()).to.contain('2001-01-01T02:30');
    await shipmentInfoUpdatePage.setActualWeightInput('5');
    expect(await shipmentInfoUpdatePage.getActualWeightInput()).to.eq('5');
    await shipmentInfoUpdatePage.setVolumetricWeightInput('5');
    expect(await shipmentInfoUpdatePage.getVolumetricWeightInput()).to.eq('5');
    await shipmentInfoUpdatePage.setLengthInput('5');
    expect(await shipmentInfoUpdatePage.getLengthInput()).to.eq('5');
    await shipmentInfoUpdatePage.setWidthInput('5');
    expect(await shipmentInfoUpdatePage.getWidthInput()).to.eq('5');
    await shipmentInfoUpdatePage.setHeightInput('5');
    expect(await shipmentInfoUpdatePage.getHeightInput()).to.eq('5');
    await shipmentInfoUpdatePage.setQuantityInput('5');
    expect(await shipmentInfoUpdatePage.getQuantityInput()).to.eq('5');
    await shipmentInfoUpdatePage.setTotalFrightInput('5');
    expect(await shipmentInfoUpdatePage.getTotalFrightInput()).to.eq('5');
    await shipmentInfoUpdatePage.setPackageDesciptionInput('packageDesciption');
    expect(await shipmentInfoUpdatePage.getPackageDesciptionInput()).to.match(/packageDesciption/);
    const selectedIsThirdParty = await shipmentInfoUpdatePage.getIsThirdPartyInput().isSelected();
    if (selectedIsThirdParty) {
      await shipmentInfoUpdatePage.getIsThirdPartyInput().click();
      expect(await shipmentInfoUpdatePage.getIsThirdPartyInput().isSelected()).to.be.false;
    } else {
      await shipmentInfoUpdatePage.getIsThirdPartyInput().click();
      expect(await shipmentInfoUpdatePage.getIsThirdPartyInput().isSelected()).to.be.true;
    }
    await shipmentInfoUpdatePage.setCarrierRefNoInput('carrierRefNo');
    expect(await shipmentInfoUpdatePage.getCarrierRefNoInput()).to.match(/carrierRefNo/);
    await shipmentInfoUpdatePage.setDeliveredDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await shipmentInfoUpdatePage.getDeliveredDateInput()).to.contain('2001-01-01T02:30');
    await shipmentInfoUpdatePage.setReceivedByInput('receivedBy');
    expect(await shipmentInfoUpdatePage.getReceivedByInput()).to.match(/receivedBy/);
    await shipmentInfoUpdatePage.setRelationShipInput('relationShip');
    expect(await shipmentInfoUpdatePage.getRelationShipInput()).to.match(/relationShip/);
    await shipmentInfoUpdatePage.carrierDetailsSelectLastOption();
    await shipmentInfoUpdatePage.shipmentTypeSelectLastOption();
    await shipmentInfoUpdatePage.shipmentModeSelectLastOption();
    await shipmentInfoUpdatePage.paymentModeSelectLastOption();
    await shipmentInfoUpdatePage.trackingStatusSelectLastOption();
    await shipmentInfoUpdatePage.vendorSelectLastOption();
    await shipmentInfoUpdatePage.originSelectLastOption();
    await shipmentInfoUpdatePage.destinationSelectLastOption();
    await waitUntilDisplayed(shipmentInfoUpdatePage.getSaveButton());
    await shipmentInfoUpdatePage.save();
    await waitUntilHidden(shipmentInfoUpdatePage.getSaveButton());
    expect(await shipmentInfoUpdatePage.getSaveButton().isPresent()).to.be.false;

    await shipmentInfoComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await shipmentInfoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last ShipmentInfo', async () => {
    await shipmentInfoComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await shipmentInfoComponentsPage.countDeleteButtons();
    await shipmentInfoComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    shipmentInfoDeleteDialog = new ShipmentInfoDeleteDialog();
    expect(await shipmentInfoDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/cargotrackerApp.shipmentInfo.delete.question/);
    await shipmentInfoDeleteDialog.clickOnConfirmButton();

    await shipmentInfoComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await shipmentInfoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
