import { element, by, ElementFinder } from 'protractor';

export default class VendorUpdatePage {
  pageTitle: ElementFinder = element(by.id('cargotrackerApp.vendor.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  vendornameInput: ElementFinder = element(by.css('input#vendor-vendorname'));
  firstNameInput: ElementFinder = element(by.css('input#vendor-firstName'));
  lastNameInput: ElementFinder = element(by.css('input#vendor-lastName'));
  mobileNoInput: ElementFinder = element(by.css('input#vendor-mobileNo'));
  addressInput: ElementFinder = element(by.css('input#vendor-address'));
  panInput: ElementFinder = element(by.css('input#vendor-pan'));
  gstInInput: ElementFinder = element(by.css('input#vendor-gstIn'));
  isActiveInput: ElementFinder = element(by.css('input#vendor-isActive'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setVendornameInput(vendorname) {
    await this.vendornameInput.sendKeys(vendorname);
  }

  async getVendornameInput() {
    return this.vendornameInput.getAttribute('value');
  }

  async setFirstNameInput(firstName) {
    await this.firstNameInput.sendKeys(firstName);
  }

  async getFirstNameInput() {
    return this.firstNameInput.getAttribute('value');
  }

  async setLastNameInput(lastName) {
    await this.lastNameInput.sendKeys(lastName);
  }

  async getLastNameInput() {
    return this.lastNameInput.getAttribute('value');
  }

  async setMobileNoInput(mobileNo) {
    await this.mobileNoInput.sendKeys(mobileNo);
  }

  async getMobileNoInput() {
    return this.mobileNoInput.getAttribute('value');
  }

  async setAddressInput(address) {
    await this.addressInput.sendKeys(address);
  }

  async getAddressInput() {
    return this.addressInput.getAttribute('value');
  }

  async setPanInput(pan) {
    await this.panInput.sendKeys(pan);
  }

  async getPanInput() {
    return this.panInput.getAttribute('value');
  }

  async setGstInInput(gstIn) {
    await this.gstInInput.sendKeys(gstIn);
  }

  async getGstInInput() {
    return this.gstInInput.getAttribute('value');
  }

  getIsActiveInput() {
    return this.isActiveInput;
  }
  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }
}
