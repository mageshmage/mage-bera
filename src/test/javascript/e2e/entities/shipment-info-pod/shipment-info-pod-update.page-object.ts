import { element, by, ElementFinder } from 'protractor';

export default class ShipmentInfoPODUpdatePage {
  pageTitle: ElementFinder = element(by.id('cargotrackerApp.shipmentInfoPOD.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  podInput: ElementFinder = element(by.css('input#file_pod'));
  commentsInput: ElementFinder = element(by.css('input#shipment-info-pod-comments'));
  shipmentInfoSelect: ElementFinder = element(by.css('select#shipment-info-pod-shipmentInfo'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setPodInput(pod) {
    await this.podInput.sendKeys(pod);
  }

  async getPodInput() {
    return this.podInput.getAttribute('value');
  }

  async setCommentsInput(comments) {
    await this.commentsInput.sendKeys(comments);
  }

  async getCommentsInput() {
    return this.commentsInput.getAttribute('value');
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
