import { element, by, ElementFinder } from 'protractor';

export default class StateUpdatePage {
  pageTitle: ElementFinder = element(by.id('cargotrackerApp.state.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  stateCodeInput: ElementFinder = element(by.css('input#state-stateCode'));
  stateNameInput: ElementFinder = element(by.css('input#state-stateName'));
  countrySelect: ElementFinder = element(by.css('select#state-country'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setStateCodeInput(stateCode) {
    await this.stateCodeInput.sendKeys(stateCode);
  }

  async getStateCodeInput() {
    return this.stateCodeInput.getAttribute('value');
  }

  async setStateNameInput(stateName) {
    await this.stateNameInput.sendKeys(stateName);
  }

  async getStateNameInput() {
    return this.stateNameInput.getAttribute('value');
  }

  async countrySelectLastOption() {
    await this.countrySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async countrySelectOption(option) {
    await this.countrySelect.sendKeys(option);
  }

  getCountrySelect() {
    return this.countrySelect;
  }

  async getCountrySelectedOption() {
    return this.countrySelect.element(by.css('option:checked')).getText();
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
