import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { useDispatch, useSelector } from 'react-redux';
import { Button } from '@mui/material';
import toast from 'react-hot-toast';
import TextInput from '../../../../components/ui/forms/TextInput';
import SelectDropdown from '../../../../components/ui/forms/SelectDropdown';
import ErrorDisplay from '../../../../components/ui/feedback/ErrorDisplay';
import { createDashboardProduct, loadCategoryList, modifyDashboardProduct } from '../../../../store/actions';

const AddProductForm = ({ setOpen, product, update = false, navigate, pathname }) => {
  const dispatch = useDispatch();
  const [isLoading, setIsLoading] = useState(false);
  const [pickedCategory, setPickedCategory] = useState();

  const { categoryList } = useSelector((state) => state.catalog);
  const { categoryLoader, errorMessage } = useSelector((state) => state.errors);

  const {
    register,
    handleSubmit,
    reset,
    setValue,
    formState: { errors },
  } = useForm({
    mode: 'onTouched',
  });

  const handleProductSave = (formData) => {
    if (!update) {
      const newProductData = {
        ...formData,
        categoryId: pickedCategory.categoryId,
      };
      dispatch(createDashboardProduct(newProductData, toast, reset, setIsLoading, setOpen));
    } else {
      const updatedProductData = {
        ...formData,
        id: product.id,
      };
      dispatch(modifyDashboardProduct(updatedProductData, toast, reset, setIsLoading, setOpen, navigate, pathname));
    }
  };

  useEffect(() => {
    if (update && product) {
      setValue('productName', product?.productName);
      setValue('model', product?.model);
      setValue('price', product?.price);
      setValue('quantity', product?.quantity);
      setValue('description', product?.description);
    }
  }, [update, product, setValue]);

  useEffect(() => {
    if (!update) {
      dispatch(loadCategoryList());
    }
  }, [dispatch, update]);

  useEffect(() => {
    if (!categoryLoader && categoryList?.length > 0) {
      setPickedCategory(categoryList[0]);
    }
  }, [categoryList, categoryLoader]);

  if (errorMessage) return <ErrorDisplay message={errorMessage} />;

  const submitButtonText = isLoading ? 'Loading...' : update ? 'Update' : 'Save';

  return (
    <div className='py-6 relative h-full bg-white'>
      <form className='space-y-6' onSubmit={handleSubmit(handleProductSave)}>
        {/* Product Name and Category */}
        <div className='flex md:flex-row flex-col gap-4 w-full'>
          <TextInput
            label='Product Name'
            required
            id='productName'
            type='text'
            message='This field is required*'
            register={register}
            placeholder='Enter product name'
            errors={errors}
          />

          {!update && (
            <SelectDropdown
              label='Select Category'
              select={pickedCategory}
              setSelect={setPickedCategory}
              lists={categoryList}
            />
          )}
        </div>

        {/* Price and Quantity */}
        <div className='flex md:flex-row flex-col gap-4 w-full'>
          <TextInput
            label='Price'
            required
            id='price'
            type='number'
            message='This field is required*'
            placeholder='Enter product price'
            register={register}
            errors={errors}
          />

          <TextInput
            label='Quantity'
            required
            id='quantity'
            type='number'
            message='This field is required*'
            register={register}
            placeholder='Enter product quantity'
            errors={errors}
          />
        </div>

        {/* Model */}
        <div className='flex md:flex-row flex-col gap-4 w-full'>
          <TextInput
            label='Model'
            id='model'
            type='text'
            message=''
            register={register}
            placeholder='Enter product model (Optional)'
            errors={errors}
          />
        </div>

        {/* Description */}
        <div className='flex flex-col gap-2 w-full'>
          <label htmlFor='description' className='font-semibold text-sm text-gray-800'>
            Description
          </label>

          <textarea
            id='description'
            rows={5}
            placeholder='Add product description...'
            className={`px-4 py-2 w-full border outline-none bg-transparent text-gray-800 rounded-lg transition-colors duration-200 ${
              errors['description']?.message ? 'border-red-500 focus:border-red-600' : 'border-gray-300 focus:border-red-600'
            }`}
            maxLength={255}
            {...register('description', {
              required: { value: true, message: 'Description is required' },
              minLength: { value: 10, message: 'Description must be at least 10 characters' },
              maxLength: { value: 255, message: 'Description must not exceed 255 characters' },
            })}
          />

          {errors['description']?.message && (
            <p className='text-sm font-semibold text-red-600 mt-1'>{errors['description']?.message}</p>
          )}
        </div>

        {/* Cancel and Save Buttons */}
        <div className='flex w-full justify-between items-center absolute bottom-6 left-0 right-0 px-8'>
          <Button
            disabled={isLoading}
            onClick={() => setOpen(false)}
            variant='outlined'
            className='text-gray-700 py-2 px-6 text-sm font-medium border-gray-300'
          >
            Cancel
          </Button>

          <Button
            disabled={isLoading}
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

export default AddProductForm;
