import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity, ICrudGetAllActionByVendor } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPaymentMode, defaultValue } from 'app/shared/model/payment-mode.model';

export const ACTION_TYPES = {
  SEARCH_PAYMENTMODES: 'paymentMode/SEARCH_PAYMENTMODES',
  FETCH_PAYMENTMODE_LIST: 'paymentMode/FETCH_PAYMENTMODE_LIST',
  FETCH_PAYMENTMODE: 'paymentMode/FETCH_PAYMENTMODE',
  CREATE_PAYMENTMODE: 'paymentMode/CREATE_PAYMENTMODE',
  UPDATE_PAYMENTMODE: 'paymentMode/UPDATE_PAYMENTMODE',
  DELETE_PAYMENTMODE: 'paymentMode/DELETE_PAYMENTMODE',
  RESET: 'paymentMode/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPaymentMode>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PaymentModeState = Readonly<typeof initialState>;

// Reducer

export default (state: PaymentModeState = initialState, action): PaymentModeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PAYMENTMODES):
    case REQUEST(ACTION_TYPES.FETCH_PAYMENTMODE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PAYMENTMODE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PAYMENTMODE):
    case REQUEST(ACTION_TYPES.UPDATE_PAYMENTMODE):
    case REQUEST(ACTION_TYPES.DELETE_PAYMENTMODE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_PAYMENTMODES):
    case FAILURE(ACTION_TYPES.FETCH_PAYMENTMODE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PAYMENTMODE):
    case FAILURE(ACTION_TYPES.CREATE_PAYMENTMODE):
    case FAILURE(ACTION_TYPES.UPDATE_PAYMENTMODE):
    case FAILURE(ACTION_TYPES.DELETE_PAYMENTMODE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PAYMENTMODES):
    case SUCCESS(ACTION_TYPES.FETCH_PAYMENTMODE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PAYMENTMODE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PAYMENTMODE):
    case SUCCESS(ACTION_TYPES.UPDATE_PAYMENTMODE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PAYMENTMODE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/payment-modes';
const apiUrlByVendor = 'api/payment-modes-byvendor';
const apiSearchUrl = 'api/_search/payment-modes';

// Actions

export const getSearchEntities: ICrudSearchAction<IPaymentMode> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PAYMENTMODES,
  payload: axios.get<IPaymentMode>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IPaymentMode> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PAYMENTMODE_LIST,
  payload: axios.get<IPaymentMode>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getPaymentModesByVendorId: ICrudGetAllActionByVendor<IPaymentMode> = (vendorId, page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PAYMENTMODE_LIST,
  payload: axios.get<IPaymentMode>(`${apiUrlByVendor}/${vendorId}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPaymentMode> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PAYMENTMODE,
    payload: axios.get<IPaymentMode>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPaymentMode> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PAYMENTMODE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPaymentMode> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PAYMENTMODE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPaymentMode> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PAYMENTMODE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
