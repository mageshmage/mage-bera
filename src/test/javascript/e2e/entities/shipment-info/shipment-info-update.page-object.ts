import { element, by, ElementFinder } from 'protractor';

export default class ShipmentInfoUpdatePage {
  pageTitle: ElementFinder = element(by.id('cargotrackerApp.shipmentInfo.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  consignmentNoInput: ElementFinder = element(by.css('input#shipment-info-consignmentNo'));
  isThirdPartyInput: ElementFinder = element(by.css('input#shipment-info-isThirdParty'));
  courierInput: ElementFinder = element(by.css('input#shipment-info-courier'));
  carrierRefNoInput: ElementFinder = element(by.css('input#shipment-info-carrierRefNo'));
  pickupDateInput: ElementFinder = element(by.css('input#shipment-info-pickupDate'));
  expectedDeliveryDateInput: ElementFinder = element(by.css('input#shipment-info-expectedDeliveryDate'));
  weightInput: ElementFinder = element(by.css('input#shipment-info-weight'));
  quantityInput: ElementFinder = element(by.css('input#shipment-info-quantity'));
  totalFrightInput: ElementFinder = element(by.css('input#shipment-info-totalFright'));
  packageDesciptionInput: ElementFinder = element(by.css('input#shipment-info-packageDesciption'));
  commentsInput: ElementFinder = element(by.css('input#shipment-info-comments'));
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

  getIsThirdPartyInput() {
    return this.isThirdPartyInput;
  }
  async setCourierInput(courier) {
    await this.courierInput.sendKeys(courier);
  }

  async getCourierInput() {
    return this.courierInput.getAttribute('value');
  }

  async setCarrierRefNoInput(carrierRefNo) {
    await this.carrierRefNoInput.sendKeys(carrierRefNo);
  }

  async getCarrierRefNoInput() {
    return this.carrierRefNoInput.getAttribute('value');
  }

  async setPickupDateInput(pickupDate) {
    await this.pickupDateInput.sendKeys(pickupDate);
  }

  async getPickupDateInput() {
    return this.pickupDateInput.getAttribute('value');
  }

  async setExpectedDeliveryDateInput(expectedDeliveryDate) {
    await this.expectedDeliveryDateInput.sendKeys(expectedDeliveryDate);
  }

  async getExpectedDeliveryDateInput() {
    return this.expectedDeliveryDateInput.getAttribute('value');
  }

  async setWeightInput(weight) {
    await this.weightInput.sendKeys(weight);
  }

  async getWeightInput() {
    return this.weightInput.getAttribute('value');
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

  async setCommentsInput(comments) {
    await this.commentsInput.sendKeys(comments);
  }

  async getCommentsInput() {
    return this.commentsInput.getAttribute('value');
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
