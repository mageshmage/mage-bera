/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import TrackingStatusComponentsPage from './tracking-status.page-object';
import { TrackingStatusDeleteDialog } from './tracking-status.page-object';
import TrackingStatusUpdatePage from './tracking-status-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('TrackingStatus e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let trackingStatusUpdatePage: TrackingStatusUpdatePage;
  let trackingStatusComponentsPage: TrackingStatusComponentsPage;
  let trackingStatusDeleteDialog: TrackingStatusDeleteDialog;

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

  it('should load TrackingStatuses', async () => {
    await navBarPage.getEntityPage('tracking-status');
    trackingStatusComponentsPage = new TrackingStatusComponentsPage();
    expect(await trackingStatusComponentsPage.getTitle().getText()).to.match(/Tracking Statuses/);
  });

  it('should load create TrackingStatus page', async () => {
    await trackingStatusComponentsPage.clickOnCreateButton();
    trackingStatusUpdatePage = new TrackingStatusUpdatePage();
    expect(await trackingStatusUpdatePage.getPageTitle().getAttribute('id')).to.match(
      /cargotrackerApp.trackingStatus.home.createOrEditLabel/
    );
  });

  it('should create and save TrackingStatuses', async () => {
    const nbButtonsBeforeCreate = await trackingStatusComponentsPage.countDeleteButtons();

    await trackingStatusUpdatePage.setValueInput('value');
    expect(await trackingStatusUpdatePage.getValueInput()).to.match(/value/);
    await trackingStatusUpdatePage.setDescInput('desc');
    expect(await trackingStatusUpdatePage.getDescInput()).to.match(/desc/);
    await trackingStatusUpdatePage.vendorSelectLastOption();
    await waitUntilDisplayed(trackingStatusUpdatePage.getSaveButton());
    await trackingStatusUpdatePage.save();
    await waitUntilHidden(trackingStatusUpdatePage.getSaveButton());
    expect(await trackingStatusUpdatePage.getSaveButton().isPresent()).to.be.false;

    await trackingStatusComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await trackingStatusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last TrackingStatus', async () => {
    await trackingStatusComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await trackingStatusComponentsPage.countDeleteButtons();
    await trackingStatusComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    trackingStatusDeleteDialog = new TrackingStatusDeleteDialog();
    expect(await trackingStatusDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/cargotrackerApp.trackingStatus.delete.question/);
    await trackingStatusDeleteDialog.clickOnConfirmButton();

    await trackingStatusComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await trackingStatusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
