import { element, by, ElementFinder } from 'protractor';

export default class ShiperReceiverInfoUpdatePage {
  pageTitle: ElementFinder = element(by.id('cargotrackerApp.shiperReceiverInfo.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  typeSelect: ElementFinder = element(by.css('select#shiper-receiver-info-type'));
  shipperNameInput: ElementFinder = element(by.css('input#shiper-receiver-info-shipperName'));
  phoneNoInput: ElementFinder = element(by.css('input#shiper-receiver-info-phoneNo'));
  addressInput: ElementFinder = element(by.css('input#shiper-receiver-info-address'));
  emailIdInput: ElementFinder = element(by.css('input#shiper-receiver-info-emailId'));
  shipmentInfoSelect: ElementFinder = element(by.css('select#shiper-receiver-info-shipmentInfo'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setTypeSelect(type) {
    await this.typeSelect.sendKeys(type);
  }

  async getTypeSelect() {
    return this.typeSelect.element(by.css('option:checked')).getText();
  }

  async typeSelectLastOption() {
    await this.typeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }
  async setShipperNameInput(shipperName) {
    await this.shipperNameInput.sendKeys(shipperName);
  }

  async getShipperNameInput() {
    return this.shipperNameInput.getAttribute('value');
  }

  async setPhoneNoInput(phoneNo) {
    await this.phoneNoInput.sendKeys(phoneNo);
  }

  async getPhoneNoInput() {
    return this.phoneNoInput.getAttribute('value');
  }

  async setAddressInput(address) {
    await this.addressInput.sendKeys(address);
  }

  async getAddressInput() {
    return this.addressInput.getAttribute('value');
  }

  async setEmailIdInput(emailId) {
    await this.emailIdInput.sendKeys(emailId);
  }

  async getEmailIdInput() {
    return this.emailIdInput.getAttribute('value');
  }

  async shipmentInfoSelectLastOption() {
    await this.shipmentInfoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async shipmentInfoSelectOption(option) {
    await this.shipmentInfoSelect.sendKeys(option);
  }

  getShipmentInfoSelect() {
    return this.shipmentInfoSelect;
  }

  async getShipmentInfoSelectedOption() {
    return this.shipmentInfoSelect.element(by.css('option:checked')).getText();
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
