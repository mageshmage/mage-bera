export interface ITrackingStatus {
  id?: number;
  value?: string;
  desc?: string;
  vendorId?: number;
}

export const defaultValue: Readonly<ITrackingStatus> = {};
