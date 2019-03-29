/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import PaymentModeComponentsPage from './payment-mode.page-object';
import { PaymentModeDeleteDialog } from './payment-mode.page-object';
import PaymentModeUpdatePage from './payment-mode-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('PaymentMode e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let paymentModeUpdatePage: PaymentModeUpdatePage;
  let paymentModeComponentsPage: PaymentModeComponentsPage;
  let paymentModeDeleteDialog: PaymentModeDeleteDialog;

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

  it('should load PaymentModes', async () => {
    await navBarPage.getEntityPage('payment-mode');
    paymentModeComponentsPage = new PaymentModeComponentsPage();
    expect(await paymentModeComponentsPage.getTitle().getText()).to.match(/Payment Modes/);
  });

  it('should load create PaymentMode page', async () => {
    await paymentModeComponentsPage.clickOnCreateButton();
    paymentModeUpdatePage = new PaymentModeUpdatePage();
    expect(await paymentModeUpdatePage.getPageTitle().getAttribute('id')).to.match(/cargotrackerApp.paymentMode.home.createOrEditLabel/);
  });

  it('should create and save PaymentModes', async () => {
    const nbButtonsBeforeCreate = await paymentModeComponentsPage.countDeleteButtons();

    await paymentModeUpdatePage.setValueInput('value');
    expect(await paymentModeUpdatePage.getValueInput()).to.match(/value/);
    await paymentModeUpdatePage.setDescInput('desc');
    expect(await paymentModeUpdatePage.getDescInput()).to.match(/desc/);
    await paymentModeUpdatePage.vendorSelectLastOption();
    await waitUntilDisplayed(paymentModeUpdatePage.getSaveButton());
    await paymentModeUpdatePage.save();
    await waitUntilHidden(paymentModeUpdatePage.getSaveButton());
    expect(await paymentModeUpdatePage.getSaveButton().isPresent()).to.be.false;

    await paymentModeComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await paymentModeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last PaymentMode', async () => {
    await paymentModeComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await paymentModeComponentsPage.countDeleteButtons();
    await paymentModeComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    paymentModeDeleteDialog = new PaymentModeDeleteDialog();
    expect(await paymentModeDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/cargotrackerApp.paymentMode.delete.question/);
    await paymentModeDeleteDialog.clickOnConfirmButton();

    await paymentModeComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await paymentModeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
