import { ICity } from 'app/shared/model/city.model';

export interface IState {
  id?: number;
  stateCode?: string;
  stateName?: string;
  cities?: ICity[];
  countryId?: number;
  countryName?: string;
}

export const defaultValue: Readonly<IState> = {};
