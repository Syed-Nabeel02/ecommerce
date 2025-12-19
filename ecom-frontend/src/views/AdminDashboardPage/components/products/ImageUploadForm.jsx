import { useRef, useState } from 'react';
import { useDispatch } from 'react-redux';
import { Button } from '@mui/material';
import { FaCloudUploadAlt } from 'react-icons/fa';
import toast from 'react-hot-toast';
import { uploadProductImageDashboard } from '../../../../store/actions';

const ALLOWED_IMAGE_TYPES = ['image/jpeg', 'image/jpg', 'image/png'];

const ImageUploadForm = ({ setOpen, product }) => {
  const fileInputRef = useRef();
  const dispatch = useDispatch();
  const [isLoading, setIsLoading] = useState(false);
  const [previewImageData, setPreviewImageData] = useState(null);
  const [chosenFile, setChosenFile] = useState(null);

  const handleImageSelection = (event) => {
    const selectedFile = event.target.files[0];

    if (selectedFile && ALLOWED_IMAGE_TYPES.includes(selectedFile.type)) {
      const fileReader = new FileReader();
      fileReader.onloadend = () => {
        setPreviewImageData(fileReader.result);
      };
      fileReader.readAsDataURL(selectedFile);
      setChosenFile(selectedFile);
    } else {
      toast.error('Please select a valid image file (.jpeg, .jpg, .png)');
      setPreviewImageData(null);
      setChosenFile(null);
    }
  };

  const handleImageUpload = async (event) => {
    event.preventDefault();

    if (!chosenFile) {
      toast.error('Please select an image before saving.');
      return;
    }

    const imageFormData = new FormData();
    imageFormData.append('image', chosenFile);

    dispatch(uploadProductImageDashboard(imageFormData, product.id, toast, setIsLoading, setOpen));
  };

  const handleImageClear = () => {
    setPreviewImageData(null);
    setChosenFile(null);
    fileInputRef.current.value = null;
  };

  const submitButtonText = isLoading ? 'Loading...' : 'Update';

  return (
    <div className='py-6 relative h-full bg-white'>
      <form className='space-y-6' onSubmit={handleImageUpload}>
        <div className='flex flex-col gap-4 w-full'>
          <label className='flex items-center justify-center gap-3 cursor-pointer text-red-600 border-2 border-dashed border-red-300 hover:border-red-500 hover:bg-red-50 rounded-lg p-6 w-full transition-all duration-200'>
            <FaCloudUploadAlt size={28} />
            <span className='font-medium text-sm'>Upload Product Image</span>
            <input
              type='file'
              ref={fileInputRef}
              onChange={handleImageSelection}
              className='hidden'
              accept='.jpeg, .jpg, .png'
              aria-label='Upload product image'
            />
          </label>

          {previewImageData && (
            <div className='space-y-3'>
              <img
                src={previewImageData}
                alt='Image Preview'
                className='h-64 w-full object-cover rounded-lg border border-gray-200 shadow-sm'
              />
              <button
                type='button'
                onClick={handleImageClear}
                className='bg-red-600 hover:bg-red-700 text-white px-4 py-2 rounded-lg font-medium text-sm transition-colors duration-200 w-full'
              >
                Clear Image
              </button>
            </div>
          )}
        </div>

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

export default ImageUploadForm;
