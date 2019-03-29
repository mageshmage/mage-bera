import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICarrierDetails, defaultValue } from 'app/shared/model/carrier-details.model';

export const ACTION_TYPES = {
  SEARCH_CARRIERDETAILS: 'carrierDetails/SEARCH_CARRIERDETAILS',
  FETCH_CARRIERDETAILS_LIST: 'carrierDetails/FETCH_CARRIERDETAILS_LIST',
  FETCH_CARRIERDETAILS: 'carrierDetails/FETCH_CARRIERDETAILS',
  CREATE_CARRIERDETAILS: 'carrierDetails/CREATE_CARRIERDETAILS',
  UPDATE_CARRIERDETAILS: 'carrierDetails/UPDATE_CARRIERDETAILS',
  DELETE_CARRIERDETAILS: 'carrierDetails/DELETE_CARRIERDETAILS',
  RESET: 'carrierDetails/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICarrierDetails>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type CarrierDetailsState = Readonly<typeof initialState>;

// Reducer

export default (state: CarrierDetailsState = initialState, action): CarrierDetailsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_CARRIERDETAILS):
    case REQUEST(ACTION_TYPES.FETCH_CARRIERDETAILS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CARRIERDETAILS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CARRIERDETAILS):
    case REQUEST(ACTION_TYPES.UPDATE_CARRIERDETAILS):
    case REQUEST(ACTION_TYPES.DELETE_CARRIERDETAILS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_CARRIERDETAILS):
    case FAILURE(ACTION_TYPES.FETCH_CARRIERDETAILS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CARRIERDETAILS):
    case FAILURE(ACTION_TYPES.CREATE_CARRIERDETAILS):
    case FAILURE(ACTION_TYPES.UPDATE_CARRIERDETAILS):
    case FAILURE(ACTION_TYPES.DELETE_CARRIERDETAILS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_CARRIERDETAILS):
    case SUCCESS(ACTION_TYPES.FETCH_CARRIERDETAILS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CARRIERDETAILS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CARRIERDETAILS):
    case SUCCESS(ACTION_TYPES.UPDATE_CARRIERDETAILS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CARRIERDETAILS):
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

const apiUrl = 'api/carrier-details';
const apiSearchUrl = 'api/_search/carrier-details';

// Actions

export const getSearchEntities: ICrudSearchAction<ICarrierDetails> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_CARRIERDETAILS,
  payload: axios.get<ICarrierDetails>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<ICarrierDetails> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CARRIERDETAILS_LIST,
  payload: axios.get<ICarrierDetails>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ICarrierDetails> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CARRIERDETAILS,
    payload: axios.get<ICarrierDetails>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICarrierDetails> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CARRIERDETAILS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICarrierDetails> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CARRIERDETAILS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICarrierDetails> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CARRIERDETAILS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
