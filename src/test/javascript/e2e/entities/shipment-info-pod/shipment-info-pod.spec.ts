/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ShipmentInfoPODComponentsPage from './shipment-info-pod.page-object';
import { ShipmentInfoPODDeleteDialog } from './shipment-info-pod.page-object';
import ShipmentInfoPODUpdatePage from './shipment-info-pod-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';
import path from 'path';

const expect = chai.expect;

describe('ShipmentInfoPOD e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let shipmentInfoPODUpdatePage: ShipmentInfoPODUpdatePage;
  let shipmentInfoPODComponentsPage: ShipmentInfoPODComponentsPage;
  let shipmentInfoPODDeleteDialog: ShipmentInfoPODDeleteDialog;
  const fileToUpload = '../../../../../main/webapp/static/images/logo-jhipster.png';
  const absolutePath = path.resolve(__dirname, fileToUpload);

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

  it('should load ShipmentInfoPODS', async () => {
    await navBarPage.getEntityPage('shipment-info-pod');
    shipmentInfoPODComponentsPage = new ShipmentInfoPODComponentsPage();
    expect(await shipmentInfoPODComponentsPage.getTitle().getText()).to.match(/Shipment Info PODS/);
  });

  it('should load create ShipmentInfoPOD page', async () => {
    await shipmentInfoPODComponentsPage.clickOnCreateButton();
    shipmentInfoPODUpdatePage = new ShipmentInfoPODUpdatePage();
    expect(await shipmentInfoPODUpdatePage.getPageTitle().getAttribute('id')).to.match(
      /cargotrackerApp.shipmentInfoPOD.home.createOrEditLabel/
    );
  });

  it('should create and save ShipmentInfoPODS', async () => {
    const nbButtonsBeforeCreate = await shipmentInfoPODComponentsPage.countDeleteButtons();

    await shipmentInfoPODUpdatePage.setPodInput(absolutePath);
    await shipmentInfoPODUpdatePage.setCommentsInput('comments');
    expect(await shipmentInfoPODUpdatePage.getCommentsInput()).to.match(/comments/);
    await shipmentInfoPODUpdatePage.shipmentInfoSelectLastOption();
    await waitUntilDisplayed(shipmentInfoPODUpdatePage.getSaveButton());
    await shipmentInfoPODUpdatePage.save();
    await waitUntilHidden(shipmentInfoPODUpdatePage.getSaveButton());
    expect(await shipmentInfoPODUpdatePage.getSaveButton().isPresent()).to.be.false;

    await shipmentInfoPODComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await shipmentInfoPODComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last ShipmentInfoPOD', async () => {
    await shipmentInfoPODComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await shipmentInfoPODComponentsPage.countDeleteButtons();
    await shipmentInfoPODComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    shipmentInfoPODDeleteDialog = new ShipmentInfoPODDeleteDialog();
    expect(await shipmentInfoPODDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /cargotrackerApp.shipmentInfoPOD.delete.question/
    );
    await shipmentInfoPODDeleteDialog.clickOnConfirmButton();

    await shipmentInfoPODComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await shipmentInfoPODComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
