/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import VendorComponentsPage from './vendor.page-object';
import { VendorDeleteDialog } from './vendor.page-object';
import VendorUpdatePage from './vendor-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('Vendor e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let vendorUpdatePage: VendorUpdatePage;
  let vendorComponentsPage: VendorComponentsPage;
  let vendorDeleteDialog: VendorDeleteDialog;

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

  it('should load Vendors', async () => {
    await navBarPage.getEntityPage('vendor');
    vendorComponentsPage = new VendorComponentsPage();
    expect(await vendorComponentsPage.getTitle().getText()).to.match(/Vendors/);
  });

  it('should load create Vendor page', async () => {
    await vendorComponentsPage.clickOnCreateButton();
    vendorUpdatePage = new VendorUpdatePage();
    expect(await vendorUpdatePage.getPageTitle().getAttribute('id')).to.match(/cargotrackerApp.vendor.home.createOrEditLabel/);
  });

  it('should create and save Vendors', async () => {
    const nbButtonsBeforeCreate = await vendorComponentsPage.countDeleteButtons();

    await vendorUpdatePage.setNameInput('name');
    expect(await vendorUpdatePage.getNameInput()).to.match(/name/);
    await vendorUpdatePage.setPanInput('pan');
    expect(await vendorUpdatePage.getPanInput()).to.match(/pan/);
    await vendorUpdatePage.setGstinInput('gstin');
    expect(await vendorUpdatePage.getGstinInput()).to.match(/gstin/);
    await waitUntilDisplayed(vendorUpdatePage.getSaveButton());
    await vendorUpdatePage.save();
    await waitUntilHidden(vendorUpdatePage.getSaveButton());
    expect(await vendorUpdatePage.getSaveButton().isPresent()).to.be.false;

    await vendorComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await vendorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Vendor', async () => {
    await vendorComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await vendorComponentsPage.countDeleteButtons();
    await vendorComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    vendorDeleteDialog = new VendorDeleteDialog();
    expect(await vendorDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/cargotrackerApp.vendor.delete.question/);
    await vendorDeleteDialog.clickOnConfirmButton();

    await vendorComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await vendorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
