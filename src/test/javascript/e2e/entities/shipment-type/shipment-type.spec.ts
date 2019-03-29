/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ShipmentTypeComponentsPage from './shipment-type.page-object';
import { ShipmentTypeDeleteDialog } from './shipment-type.page-object';
import ShipmentTypeUpdatePage from './shipment-type-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('ShipmentType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let shipmentTypeUpdatePage: ShipmentTypeUpdatePage;
  let shipmentTypeComponentsPage: ShipmentTypeComponentsPage;
  let shipmentTypeDeleteDialog: ShipmentTypeDeleteDialog;

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

  it('should load ShipmentTypes', async () => {
    await navBarPage.getEntityPage('shipment-type');
    shipmentTypeComponentsPage = new ShipmentTypeComponentsPage();
    expect(await shipmentTypeComponentsPage.getTitle().getText()).to.match(/Shipment Types/);
  });

  it('should load create ShipmentType page', async () => {
    await shipmentTypeComponentsPage.clickOnCreateButton();
    shipmentTypeUpdatePage = new ShipmentTypeUpdatePage();
    expect(await shipmentTypeUpdatePage.getPageTitle().getAttribute('id')).to.match(/cargotrackerApp.shipmentType.home.createOrEditLabel/);
  });

  it('should create and save ShipmentTypes', async () => {
    const nbButtonsBeforeCreate = await shipmentTypeComponentsPage.countDeleteButtons();

    await shipmentTypeUpdatePage.setValueInput('value');
    expect(await shipmentTypeUpdatePage.getValueInput()).to.match(/value/);
    await shipmentTypeUpdatePage.setDescInput('desc');
    expect(await shipmentTypeUpdatePage.getDescInput()).to.match(/desc/);
    await shipmentTypeUpdatePage.vendorSelectLastOption();
    await waitUntilDisplayed(shipmentTypeUpdatePage.getSaveButton());
    await shipmentTypeUpdatePage.save();
    await waitUntilHidden(shipmentTypeUpdatePage.getSaveButton());
    expect(await shipmentTypeUpdatePage.getSaveButton().isPresent()).to.be.false;

    await shipmentTypeComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await shipmentTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last ShipmentType', async () => {
    await shipmentTypeComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await shipmentTypeComponentsPage.countDeleteButtons();
    await shipmentTypeComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    shipmentTypeDeleteDialog = new ShipmentTypeDeleteDialog();
    expect(await shipmentTypeDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/cargotrackerApp.shipmentType.delete.question/);
    await shipmentTypeDeleteDialog.clickOnConfirmButton();

    await shipmentTypeComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await shipmentTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
