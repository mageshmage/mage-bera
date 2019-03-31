export interface ICity {
  id?: number;
  cityCode?: string;
  cityName?: string;
  stateId?: number;
}

export const defaultValue: Readonly<ICity> = {};
