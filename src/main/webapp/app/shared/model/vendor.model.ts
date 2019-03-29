export interface IVendor {
  id?: number;
  name?: string;
  pan?: string;
  gstin?: string;
}

export const defaultValue: Readonly<IVendor> = {};
