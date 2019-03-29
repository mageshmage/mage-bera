import { IState } from 'app/shared/model/state.model';

export interface ICountry {
  id?: number;
  countryCode?: string;
  countryName?: string;
  states?: IState[];
}

export const defaultValue: Readonly<ICountry> = {};
