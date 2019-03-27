import { element, by, ElementFinder } from 'protractor';

export default class VendorUpdatePage {
  pageTitle: ElementFinder = element(by.id('cargotrackerApp.vendor.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  nameInput: ElementFinder = element(by.css('input#vendor-name'));
  panInput: ElementFinder = element(by.css('input#vendor-pan'));
  gstinInput: ElementFinder = element(by.css('input#vendor-gstin'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return this.nameInput.getAttribute('value');
  }

  async setPanInput(pan) {
    await this.panInput.sendKeys(pan);
  }

  async getPanInput() {
    return this.panInput.getAttribute('value');
  }

  async setGstinInput(gstin) {
    await this.gstinInput.sendKeys(gstin);
  }

  async getGstinInput() {
    return this.gstinInput.getAttribute('value');
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
