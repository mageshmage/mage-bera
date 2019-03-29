export interface IVendor {
  id?: number;
  vendorname?: string;
  firstName?: string;
  lastName?: string;
  mobileNo?: string;
  address?: string;
  pan?: string;
  gstIn?: string;
  isActive?: boolean;
}

export const defaultValue: Readonly<IVendor> = {
  isActive: false
};
