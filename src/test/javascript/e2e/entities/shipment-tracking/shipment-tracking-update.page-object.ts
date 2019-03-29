import { element, by, ElementFinder } from 'protractor';

export default class ShipmentTrackingUpdatePage {
  pageTitle: ElementFinder = element(by.id('cargotrackerApp.shipmentTracking.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  trackingDateInput: ElementFinder = element(by.css('input#shipment-tracking-trackingDate'));
  activitiesInput: ElementFinder = element(by.css('input#shipment-tracking-activities'));
  shipmentInfoSelect: ElementFinder = element(by.css('select#shipment-tracking-shipmentInfo'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setTrackingDateInput(trackingDate) {
    await this.trackingDateInput.sendKeys(trackingDate);
  }

  async getTrackingDateInput() {
    return this.trackingDateInput.getAttribute('value');
  }

  async setActivitiesInput(activities) {
    await this.activitiesInput.sendKeys(activities);
  }

  async getActivitiesInput() {
    return this.activitiesInput.getAttribute('value');
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
