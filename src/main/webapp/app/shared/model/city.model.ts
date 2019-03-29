import { IState } from 'app/shared/model/state.model';

export interface ICity {
  id?: number;
  cityCode?: string;
  cityName?: string;
  state?: IState;
}

export const defaultValue: Readonly<ICity> = {};
