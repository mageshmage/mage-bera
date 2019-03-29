import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IShiperReceiverInfo, defaultValue } from 'app/shared/model/shiper-receiver-info.model';

export const ACTION_TYPES = {
  SEARCH_SHIPERRECEIVERINFOS: 'shiperReceiverInfo/SEARCH_SHIPERRECEIVERINFOS',
  FETCH_SHIPERRECEIVERINFO_LIST: 'shiperReceiverInfo/FETCH_SHIPERRECEIVERINFO_LIST',
  FETCH_SHIPERRECEIVERINFO: 'shiperReceiverInfo/FETCH_SHIPERRECEIVERINFO',
  CREATE_SHIPERRECEIVERINFO: 'shiperReceiverInfo/CREATE_SHIPERRECEIVERINFO',
  UPDATE_SHIPERRECEIVERINFO: 'shiperReceiverInfo/UPDATE_SHIPERRECEIVERINFO',
  DELETE_SHIPERRECEIVERINFO: 'shiperReceiverInfo/DELETE_SHIPERRECEIVERINFO',
  RESET: 'shiperReceiverInfo/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IShiperReceiverInfo>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ShiperReceiverInfoState = Readonly<typeof initialState>;

// Reducer

export default (state: ShiperReceiverInfoState = initialState, action): ShiperReceiverInfoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_SHIPERRECEIVERINFOS):
    case REQUEST(ACTION_TYPES.FETCH_SHIPERRECEIVERINFO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SHIPERRECEIVERINFO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SHIPERRECEIVERINFO):
    case REQUEST(ACTION_TYPES.UPDATE_SHIPERRECEIVERINFO):
    case REQUEST(ACTION_TYPES.DELETE_SHIPERRECEIVERINFO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_SHIPERRECEIVERINFOS):
    case FAILURE(ACTION_TYPES.FETCH_SHIPERRECEIVERINFO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SHIPERRECEIVERINFO):
    case FAILURE(ACTION_TYPES.CREATE_SHIPERRECEIVERINFO):
    case FAILURE(ACTION_TYPES.UPDATE_SHIPERRECEIVERINFO):
    case FAILURE(ACTION_TYPES.DELETE_SHIPERRECEIVERINFO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_SHIPERRECEIVERINFOS):
    case SUCCESS(ACTION_TYPES.FETCH_SHIPERRECEIVERINFO_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SHIPERRECEIVERINFO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SHIPERRECEIVERINFO):
    case SUCCESS(ACTION_TYPES.UPDATE_SHIPERRECEIVERINFO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SHIPERRECEIVERINFO):
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

const apiUrl = 'api/shiper-receiver-infos';
const apiSearchUrl = 'api/_search/shiper-receiver-infos';

// Actions

export const getSearchEntities: ICrudSearchAction<IShiperReceiverInfo> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_SHIPERRECEIVERINFOS,
  payload: axios.get<IShiperReceiverInfo>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IShiperReceiverInfo> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SHIPERRECEIVERINFO_LIST,
    payload: axios.get<IShiperReceiverInfo>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IShiperReceiverInfo> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SHIPERRECEIVERINFO,
    payload: axios.get<IShiperReceiverInfo>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IShiperReceiverInfo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SHIPERRECEIVERINFO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IShiperReceiverInfo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SHIPERRECEIVERINFO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IShiperReceiverInfo> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SHIPERRECEIVERINFO,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
