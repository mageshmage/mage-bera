/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ShipmentModeComponentsPage from './shipment-mode.page-object';
import { ShipmentModeDeleteDialog } from './shipment-mode.page-object';
import ShipmentModeUpdatePage from './shipment-mode-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('ShipmentMode e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let shipmentModeUpdatePage: ShipmentModeUpdatePage;
  let shipmentModeComponentsPage: ShipmentModeComponentsPage;
  let shipmentModeDeleteDialog: ShipmentModeDeleteDialog;

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

  it('should load ShipmentModes', async () => {
    await navBarPage.getEntityPage('shipment-mode');
    shipmentModeComponentsPage = new ShipmentModeComponentsPage();
    expect(await shipmentModeComponentsPage.getTitle().getText()).to.match(/Shipment Modes/);
  });

  it('should load create ShipmentMode page', async () => {
    await shipmentModeComponentsPage.clickOnCreateButton();
    shipmentModeUpdatePage = new ShipmentModeUpdatePage();
    expect(await shipmentModeUpdatePage.getPageTitle().getAttribute('id')).to.match(/cargotrackerApp.shipmentMode.home.createOrEditLabel/);
  });

  it('should create and save ShipmentModes', async () => {
    const nbButtonsBeforeCreate = await shipmentModeComponentsPage.countDeleteButtons();

    await shipmentModeUpdatePage.setValueInput('value');
    expect(await shipmentModeUpdatePage.getValueInput()).to.match(/value/);
    await shipmentModeUpdatePage.setDescInput('desc');
    expect(await shipmentModeUpdatePage.getDescInput()).to.match(/desc/);
    await shipmentModeUpdatePage.vendorSelectLastOption();
    await waitUntilDisplayed(shipmentModeUpdatePage.getSaveButton());
    await shipmentModeUpdatePage.save();
    await waitUntilHidden(shipmentModeUpdatePage.getSaveButton());
    expect(await shipmentModeUpdatePage.getSaveButton().isPresent()).to.be.false;

    await shipmentModeComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await shipmentModeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last ShipmentMode', async () => {
    await shipmentModeComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await shipmentModeComponentsPage.countDeleteButtons();
    await shipmentModeComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    shipmentModeDeleteDialog = new ShipmentModeDeleteDialog();
    expect(await shipmentModeDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/cargotrackerApp.shipmentMode.delete.question/);
    await shipmentModeDeleteDialog.clickOnConfirmButton();

    await shipmentModeComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await shipmentModeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
