/**
 * AddAddressForm.jsx
 * Form for adding or editing user shipping addresses.
 * Pre-fills fields when editing an existing address.
 */

// React hooks for side effects
import { useEffect } from 'react';
// React Hook Form for form validation
import { useForm } from 'react-hook-form';
// Redux hooks for state and actions
import { useDispatch, useSelector } from 'react-redux';
// Material-UI button component
import { Button } from '@mui/material';
// Address card icon
import { FaAddressCard } from 'react-icons/fa';
// Toast notifications for user feedback
import toast from 'react-hot-toast';
// Reusable text input component
import TextInput from '../../../../components/ui/forms/TextInput';
// Redux action to save address
import { saveUserAddressData, saveUserAddressDataForCustomer } from '../../../../store/actions';

/**
 * AddAddressForm Component
 * @param {object} address - Existing address data (for edit mode), null for new address
 * @param {function} setOpenAddressModal - Function to close the modal after saving
 * @param {number} customerId - Customer ID (for admin mode)
 * @param {boolean} isAdminMode - Whether component is used in admin context
 */
const AddAddressForm = ({ address, setOpenAddressModal, customerId, isAdminMode = false }) => {
  const dispatch = useDispatch();
  // Get loading state from Redux
  const { btnLoader } = useSelector((state) => state.errors);

  // Initialize React Hook Form
  const {
    register,
    handleSubmit,
    setValue,
    formState: { errors },
  } = useForm({
    mode: 'onChange',
  });

  // Submit form to save or update address
  const handleSaveAddress = (formData) => {
    if (isAdminMode && customerId) {
      dispatch(saveUserAddressDataForCustomer(customerId, formData, toast, address?.addressId, setOpenAddressModal));
    } else {
      dispatch(saveUserAddressData(formData, toast, address?.addressId, setOpenAddressModal));
    }
  };

  // Pre-fill form fields when editing existing address
  useEffect(() => {
    if (address?.addressId) {
      setValue('buildingName', address?.buildingName);
      setValue('city', address?.city);
      setValue('street', address?.street);
      setValue('state', address?.state);
      setValue('pincode', address?.pincode);
      setValue('country', address?.country);
    }
  }, [address, setValue]);

  // Determine if we're editing or adding new address
  const isEditMode = !!address?.addressId;
  const submitButtonText = btnLoader ? 'Loading...' : isEditMode ? 'Update' : 'Save';
  const formTitle = isEditMode ? 'Update Address' : 'Add Address';

  return (
    <div className='py-6 bg-white'>
      <form className='space-y-6' onSubmit={handleSubmit(handleSaveAddress)}>
        {/* Form Header */}
        <div className='flex items-center justify-center gap-3 mb-6 font-semibold text-2xl text-gray-900'>
          <FaAddressCard className='text-red-600' size={28} />
          <h2>{formTitle}</h2>
        </div>

        {/* Address Fields */}
        <div className='flex flex-col gap-4'>
          <TextInput
            label='Building Name'
            required
            id='buildingName'
            type='text'
            message='Building Name is required (5-50 characters)*'
            placeholder='Enter building name'
            register={register}
            errors={errors}
            min={5}
            max={50}
          />

          <TextInput
            label='Street'
            required
            id='street'
            type='text'
            message='Street is required (5-50 characters)*'
            placeholder='Enter street address'
            register={register}
            errors={errors}
            min={5}
            max={50}
          />

          <div className='flex md:flex-row flex-col gap-4 w-full'>
            <TextInput
              label='City'
              required
              id='city'
              type='text'
              message='City is required (3-30 characters)*'
              placeholder='Enter city'
              register={register}
              errors={errors}
              min={3}
              max={30}
            />

            <TextInput
              label='State'
              required
              id='state'
              type='text'
              message='State is required (2-30 characters)*'
              placeholder='Enter state'
              register={register}
              errors={errors}
              min={2}
              max={30}
            />
          </div>

          <div className='flex md:flex-row flex-col gap-4 w-full'>
            <TextInput
              label='Pincode'
              required
              id='pincode'
              type='text'
              message='Pincode is required (5-10 characters)*'
              placeholder='Enter pincode'
              register={register}
              errors={errors}
              min={5}
              max={10}
            />

            <TextInput
              label='Country'
              required
              id='country'
              type='text'
              message='Country is required (3-30 characters)*'
              placeholder='Enter country'
              register={register}
              errors={errors}
              min={3}
              max={30}
            />
          </div>
        </div>

        {/* Action Buttons */}
        <div className='flex w-full justify-end gap-3 pt-4'>
          <Button
            disabled={btnLoader}
            onClick={() => setOpenAddressModal(false)}
            variant='outlined'
            className='text-gray-700 py-2 px-6 text-sm font-medium border-gray-300'
          >
            Cancel
          </Button>

          <Button
            disabled={btnLoader}
            type='submit'
            variant='contained'
            className='bg-red-600 hover:bg-red-700 disabled:bg-red-400 text-white py-2 px-6 text-sm font-medium'
          >
            {submitButtonText}
          </Button>
        </div>
      </form>
    </div>
  );
};

export default AddAddressForm;
