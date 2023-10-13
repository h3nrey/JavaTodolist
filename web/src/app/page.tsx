"use client"
import Image from 'next/image'
import { useForm } from 'react-hook-form'

export default function Home() {
  const { register, handleSubmit } = useForm();

  function onSubmit(data: any) {
    console.log(data);
  }
  return (
    <div className='text-white' onSubmit={handleSubmit(onSubmit)}>
      <form action="" className='flex flex-col'>
        <h2 className='text-[1.5rem]'>
          Cadastro
        </h2>
        <input
          {...register("username", { required: true })}
          type="text"
          placeholder='Username'
          className='bg-transparent'
        />

        <input
          {...register("password", { required: true })}
          type="text"
          placeholder='password'
          className='bg-transparent border-b-2 border-white pl-2'
        />
        <button>
          Enviar
        </button>
      </form>
    </div>
  )
}
