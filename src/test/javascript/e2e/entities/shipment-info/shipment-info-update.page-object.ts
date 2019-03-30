import { element, by, ElementFinder } from 'protractor';

export default class ShipmentInfoUpdatePage {
  pageTitle: ElementFinder = element(by.id('cargotrackerApp.shipmentInfo.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  consignmentNoInput: ElementFinder = element(by.css('input#shipment-info-consignmentNo'));
  bookingDateInput: ElementFinder = element(by.css('input#shipment-info-bookingDate'));
  expectedDeliveryDateInput: ElementFinder = element(by.css('input#shipment-info-expectedDeliveryDate'));
  actualWeightInput: ElementFinder = element(by.css('input#shipment-info-actualWeight'));
  volumetricWeightInput: ElementFinder = element(by.css('input#shipment-info-volumetricWeight'));
  lengthInput: ElementFinder = element(by.css('input#shipment-info-length'));
  widthInput: ElementFinder = element(by.css('input#shipment-info-width'));
  heightInput: ElementFinder = element(by.css('input#shipment-info-height'));
  quantityInput: ElementFinder = element(by.css('input#shipment-info-quantity'));
  totalFrightInput: ElementFinder = element(by.css('input#shipment-info-totalFright'));
  packageDesciptionInput: ElementFinder = element(by.css('input#shipment-info-packageDesciption'));
  isThirdPartyInput: ElementFinder = element(by.css('input#shipment-info-isThirdParty'));
  carrierRefNoInput: ElementFinder = element(by.css('input#shipment-info-carrierRefNo'));
  deliveredDateInput: ElementFinder = element(by.css('input#shipment-info-deliveredDate'));
  receivedByInput: ElementFinder = element(by.css('input#shipment-info-receivedBy'));
  relationShipInput: ElementFinder = element(by.css('input#shipment-info-relationShip'));
  carrierDetailsSelect: ElementFinder = element(by.css('select#shipment-info-carrierDetails'));
  shipmentTypeSelect: ElementFinder = element(by.css('select#shipment-info-shipmentType'));
  shipmentModeSelect: ElementFinder = element(by.css('select#shipment-info-shipmentMode'));
  paymentModeSelect: ElementFinder = element(by.css('select#shipment-info-paymentMode'));
  trackingStatusSelect: ElementFinder = element(by.css('select#shipment-info-trackingStatus'));
  vendorSelect: ElementFinder = element(by.css('select#shipment-info-vendor'));
  originSelect: ElementFinder = element(by.css('select#shipment-info-origin'));
  destinationSelect: ElementFinder = element(by.css('select#shipment-info-destination'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setConsignmentNoInput(consignmentNo) {
    await this.consignmentNoInput.sendKeys(consignmentNo);
  }

  async getConsignmentNoInput() {
    return this.consignmentNoInput.getAttribute('value');
  }

  async setBookingDateInput(bookingDate) {
    await this.bookingDateInput.sendKeys(bookingDate);
  }

  async getBookingDateInput() {
    return this.bookingDateInput.getAttribute('value');
  }

  async setExpectedDeliveryDateInput(expectedDeliveryDate) {
    await this.expectedDeliveryDateInput.sendKeys(expectedDeliveryDate);
  }

  async getExpectedDeliveryDateInput() {
    return this.expectedDeliveryDateInput.getAttribute('value');
  }

  async setActualWeightInput(actualWeight) {
    await this.actualWeightInput.sendKeys(actualWeight);
  }

  async getActualWeightInput() {
    return this.actualWeightInput.getAttribute('value');
  }

  async setVolumetricWeightInput(volumetricWeight) {
    await this.volumetricWeightInput.sendKeys(volumetricWeight);
  }

  async getVolumetricWeightInput() {
    return this.volumetricWeightInput.getAttribute('value');
  }

  async setLengthInput(length) {
    await this.lengthInput.sendKeys(length);
  }

  async getLengthInput() {
    return this.lengthInput.getAttribute('value');
  }

  async setWidthInput(width) {
    await this.widthInput.sendKeys(width);
  }

  async getWidthInput() {
    return this.widthInput.getAttribute('value');
  }

  async setHeightInput(height) {
    await this.heightInput.sendKeys(height);
  }

  async getHeightInput() {
    return this.heightInput.getAttribute('value');
  }

  async setQuantityInput(quantity) {
    await this.quantityInput.sendKeys(quantity);
  }

  async getQuantityInput() {
    return this.quantityInput.getAttribute('value');
  }

  async setTotalFrightInput(totalFright) {
    await this.totalFrightInput.sendKeys(totalFright);
  }

  async getTotalFrightInput() {
    return this.totalFrightInput.getAttribute('value');
  }

  async setPackageDesciptionInput(packageDesciption) {
    await this.packageDesciptionInput.sendKeys(packageDesciption);
  }

  async getPackageDesciptionInput() {
    return this.packageDesciptionInput.getAttribute('value');
  }

  getIsThirdPartyInput() {
    return this.isThirdPartyInput;
  }
  async setCarrierRefNoInput(carrierRefNo) {
    await this.carrierRefNoInput.sendKeys(carrierRefNo);
  }

  async getCarrierRefNoInput() {
    return this.carrierRefNoInput.getAttribute('value');
  }

  async setDeliveredDateInput(deliveredDate) {
    await this.deliveredDateInput.sendKeys(deliveredDate);
  }

  async getDeliveredDateInput() {
    return this.deliveredDateInput.getAttribute('value');
  }

  async setReceivedByInput(receivedBy) {
    await this.receivedByInput.sendKeys(receivedBy);
  }

  async getReceivedByInput() {
    return this.receivedByInput.getAttribute('value');
  }

  async setRelationShipInput(relationShip) {
    await this.relationShipInput.sendKeys(relationShip);
  }

  async getRelationShipInput() {
    return this.relationShipInput.getAttribute('value');
  }

  async carrierDetailsSelectLastOption() {
    await this.carrierDetailsSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async carrierDetailsSelectOption(option) {
    await this.carrierDetailsSelect.sendKeys(option);
  }

  getCarrierDetailsSelect() {
    return this.carrierDetailsSelect;
  }

  async getCarrierDetailsSelectedOption() {
    return this.carrierDetailsSelect.element(by.css('option:checked')).getText();
  }

  async shipmentTypeSelectLastOption() {
    await this.shipmentTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async shipmentTypeSelectOption(option) {
    await this.shipmentTypeSelect.sendKeys(option);
  }

  getShipmentTypeSelect() {
    return this.shipmentTypeSelect;
  }

  async getShipmentTypeSelectedOption() {
    return this.shipmentTypeSelect.element(by.css('option:checked')).getText();
  }

  async shipmentModeSelectLastOption() {
    await this.shipmentModeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async shipmentModeSelectOption(option) {
    await this.shipmentModeSelect.sendKeys(option);
  }

  getShipmentModeSelect() {
    return this.shipmentModeSelect;
  }

  async getShipmentModeSelectedOption() {
    return this.shipmentModeSelect.element(by.css('option:checked')).getText();
  }

  async paymentModeSelectLastOption() {
    await this.paymentModeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async paymentModeSelectOption(option) {
    await this.paymentModeSelect.sendKeys(option);
  }

  getPaymentModeSelect() {
    return this.paymentModeSelect;
  }

  async getPaymentModeSelectedOption() {
    return this.paymentModeSelect.element(by.css('option:checked')).getText();
  }

  async trackingStatusSelectLastOption() {
    await this.trackingStatusSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async trackingStatusSelectOption(option) {
    await this.trackingStatusSelect.sendKeys(option);
  }

  getTrackingStatusSelect() {
    return this.trackingStatusSelect;
  }

  async getTrackingStatusSelectedOption() {
    return this.trackingStatusSelect.element(by.css('option:checked')).getText();
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

  async originSelectLastOption() {
    await this.originSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async originSelectOption(option) {
    await this.originSelect.sendKeys(option);
  }

  getOriginSelect() {
    return this.originSelect;
  }

  async getOriginSelectedOption() {
    return this.originSelect.element(by.css('option:checked')).getText();
  }

  async destinationSelectLastOption() {
    await this.destinationSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async destinationSelectOption(option) {
    await this.destinationSelect.sendKeys(option);
  }

  getDestinationSelect() {
    return this.destinationSelect;
  }

  async getDestinationSelectedOption() {
    return this.destinationSelect.element(by.css('option:checked')).getText();
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
