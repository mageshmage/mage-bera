export interface ICity {
  id?: number;
  cityCode?: string;
  cityName?: string;
  stateId?: number;
  stateName?: string;
}

export const defaultValue: Readonly<ICity> = {};
