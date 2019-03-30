import { element, by, ElementFinder } from 'protractor';

export default class PaymentModeUpdatePage {
  pageTitle: ElementFinder = element(by.id('cargotrackerApp.paymentMode.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  valueInput: ElementFinder = element(by.css('input#payment-mode-value'));
  descInput: ElementFinder = element(by.css('input#payment-mode-desc'));
  vendorSelect: ElementFinder = element(by.css('select#payment-mode-vendor'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setValueInput(value) {
    await this.valueInput.sendKeys(value);
  }

  async getValueInput() {
    return this.valueInput.getAttribute('value');
  }

  async setDescInput(desc) {
    await this.descInput.sendKeys(desc);
  }

  async getDescInput() {
    return this.descInput.getAttribute('value');
  }

  async vendorSelectLastOption() {
    await this.vendorSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async vendorSelectOption(option) {
    await this.vendorSelect.sendKeys(option);
  }

  getVendorSelect() {
    return this.vendorSelect;
  }

  async getVendorSelectedOption() {
    return this.vendorSelect.element(by.css('option:checked')).getText();
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
