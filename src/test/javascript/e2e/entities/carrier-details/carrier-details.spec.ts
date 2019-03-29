/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CarrierDetailsComponentsPage from './carrier-details.page-object';
import { CarrierDetailsDeleteDialog } from './carrier-details.page-object';
import CarrierDetailsUpdatePage from './carrier-details-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('CarrierDetails e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let carrierDetailsUpdatePage: CarrierDetailsUpdatePage;
  let carrierDetailsComponentsPage: CarrierDetailsComponentsPage;
  let carrierDetailsDeleteDialog: CarrierDetailsDeleteDialog;

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

  it('should load CarrierDetails', async () => {
    await navBarPage.getEntityPage('carrier-details');
    carrierDetailsComponentsPage = new CarrierDetailsComponentsPage();
    expect(await carrierDetailsComponentsPage.getTitle().getText()).to.match(/Carrier Details/);
  });

  it('should load create CarrierDetails page', async () => {
    await carrierDetailsComponentsPage.clickOnCreateButton();
    carrierDetailsUpdatePage = new CarrierDetailsUpdatePage();
    expect(await carrierDetailsUpdatePage.getPageTitle().getAttribute('id')).to.match(
      /cargotrackerApp.carrierDetails.home.createOrEditLabel/
    );
  });

  it('should create and save CarrierDetails', async () => {
    const nbButtonsBeforeCreate = await carrierDetailsComponentsPage.countDeleteButtons();

    await carrierDetailsUpdatePage.setValueInput('value');
    expect(await carrierDetailsUpdatePage.getValueInput()).to.match(/value/);
    await carrierDetailsUpdatePage.setDescInput('desc');
    expect(await carrierDetailsUpdatePage.getDescInput()).to.match(/desc/);
    await carrierDetailsUpdatePage.vendorSelectLastOption();
    await waitUntilDisplayed(carrierDetailsUpdatePage.getSaveButton());
    await carrierDetailsUpdatePage.save();
    await waitUntilHidden(carrierDetailsUpdatePage.getSaveButton());
    expect(await carrierDetailsUpdatePage.getSaveButton().isPresent()).to.be.false;

    await carrierDetailsComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await carrierDetailsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last CarrierDetails', async () => {
    await carrierDetailsComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await carrierDetailsComponentsPage.countDeleteButtons();
    await carrierDetailsComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    carrierDetailsDeleteDialog = new CarrierDetailsDeleteDialog();
    expect(await carrierDetailsDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/cargotrackerApp.carrierDetails.delete.question/);
    await carrierDetailsDeleteDialog.clickOnConfirmButton();

    await carrierDetailsComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await carrierDetailsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
