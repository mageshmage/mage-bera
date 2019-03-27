import { ICountry } from 'app/shared/model/country.model';

export interface IState {
  id?: number;
  stateCode?: string;
  stateName?: string;
  country?: ICountry;
}

export const defaultValue: Readonly<IState> = {};
