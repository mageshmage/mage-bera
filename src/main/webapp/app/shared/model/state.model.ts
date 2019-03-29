import { ICity } from 'app/shared/model/city.model';
import { ICountry } from 'app/shared/model/country.model';

export interface IState {
  id?: number;
  stateCode?: string;
  stateName?: string;
  cities?: ICity[];
  country?: ICountry;
}

export const defaultValue: Readonly<IState> = {};
