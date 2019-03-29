/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ShiperReceiverInfoComponentsPage from './shiper-receiver-info.page-object';
import { ShiperReceiverInfoDeleteDialog } from './shiper-receiver-info.page-object';
import ShiperReceiverInfoUpdatePage from './shiper-receiver-info-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('ShiperReceiverInfo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let shiperReceiverInfoUpdatePage: ShiperReceiverInfoUpdatePage;
  let shiperReceiverInfoComponentsPage: ShiperReceiverInfoComponentsPage;
  let shiperReceiverInfoDeleteDialog: ShiperReceiverInfoDeleteDialog;

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

  it('should load ShiperReceiverInfos', async () => {
    await navBarPage.getEntityPage('shiper-receiver-info');
    shiperReceiverInfoComponentsPage = new ShiperReceiverInfoComponentsPage();
    expect(await shiperReceiverInfoComponentsPage.getTitle().getText()).to.match(/Shiper Receiver Infos/);
  });

  it('should load create ShiperReceiverInfo page', async () => {
    await shiperReceiverInfoComponentsPage.clickOnCreateButton();
    shiperReceiverInfoUpdatePage = new ShiperReceiverInfoUpdatePage();
    expect(await shiperReceiverInfoUpdatePage.getPageTitle().getAttribute('id')).to.match(
      /cargotrackerApp.shiperReceiverInfo.home.createOrEditLabel/
    );
  });

  it('should create and save ShiperReceiverInfos', async () => {
    const nbButtonsBeforeCreate = await shiperReceiverInfoComponentsPage.countDeleteButtons();

    await shiperReceiverInfoUpdatePage.typeSelectLastOption();
    await shiperReceiverInfoUpdatePage.setNameInput('name');
    expect(await shiperReceiverInfoUpdatePage.getNameInput()).to.match(/name/);
    await shiperReceiverInfoUpdatePage.setPhoneNoInput('phoneNo');
    expect(await shiperReceiverInfoUpdatePage.getPhoneNoInput()).to.match(/phoneNo/);
    await shiperReceiverInfoUpdatePage.setAddressInput('address');
    expect(await shiperReceiverInfoUpdatePage.getAddressInput()).to.match(/address/);
    await shiperReceiverInfoUpdatePage.setCityInput('city');
    expect(await shiperReceiverInfoUpdatePage.getCityInput()).to.match(/city/);
    await shiperReceiverInfoUpdatePage.setPincodeInput('pincode');
    expect(await shiperReceiverInfoUpdatePage.getPincodeInput()).to.match(/pincode/);
    await shiperReceiverInfoUpdatePage.setEmailIdInput('emailId');
    expect(await shiperReceiverInfoUpdatePage.getEmailIdInput()).to.match(/emailId/);
    await shiperReceiverInfoUpdatePage.shipmentInfoSelectLastOption();
    await waitUntilDisplayed(shiperReceiverInfoUpdatePage.getSaveButton());
    await shiperReceiverInfoUpdatePage.save();
    await waitUntilHidden(shiperReceiverInfoUpdatePage.getSaveButton());
    expect(await shiperReceiverInfoUpdatePage.getSaveButton().isPresent()).to.be.false;

    await shiperReceiverInfoComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await shiperReceiverInfoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last ShiperReceiverInfo', async () => {
    await shiperReceiverInfoComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await shiperReceiverInfoComponentsPage.countDeleteButtons();
    await shiperReceiverInfoComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    shiperReceiverInfoDeleteDialog = new ShiperReceiverInfoDeleteDialog();
    expect(await shiperReceiverInfoDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /cargotrackerApp.shiperReceiverInfo.delete.question/
    );
    await shiperReceiverInfoDeleteDialog.clickOnConfirmButton();

    await shiperReceiverInfoComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await shiperReceiverInfoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
