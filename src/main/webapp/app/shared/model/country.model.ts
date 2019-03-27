export interface ICountry {
  id?: number;
  countryCode?: string;
  countryName?: string;
}

export const defaultValue: Readonly<ICountry> = {};
